import { useState, useCallback } from 'react';
import { AnimatePresence, motion } from 'framer-motion';
import QuestionInput from './components/QuestionInput';
import DrawingBoard from './components/DrawingBoard';
import ReadingDisplay from './components/ReadingDisplay';
import { startDivination, drawCard, interpretStream } from './api/tarot';
import { CARD_BACK } from './mock/cards';

// 弧形牌堆里展示的候选牌数量（78 = 完整牌组），仅作视觉占位
const FAN_SIZE = 78;

const PHASES = {
  QUESTION: 'question',
  SPREAD: 'spread',
  DRAWING: 'drawing',
  FLIPPING: 'flipping',
  READING: 'reading',
};

export default function App() {
  const [phase, setPhase] = useState(PHASES.QUESTION);
  const [, setQuestion] = useState('');
  const [sessionId, setSessionId] = useState(null);
  const [spread, setSpread] = useState(null);
  const [fanCards, setFanCards] = useState([]);
  const [drawnCards, setDrawnCards] = useState([]);
  const [isFlipped, setIsFlipped] = useState(false);
  const [readingText, setReadingText] = useState('');
  const [readingDone, setReadingDone] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleQuestionSubmit = useCallback(async (q) => {
    setQuestion(q);
    setError('');
    setLoading(true);
    try {
      const data = await startDivination(q);
      setSessionId(data.sessionId);
      setSpread(data.spread);
      // 弧形候选牌仅作视觉占位，只显示背面
      const cards = Array.from({ length: FAN_SIZE }, (_, i) => ({
        key: `fan-${i}`,
        image: CARD_BACK,
        drawn: false,
      }));
      setFanCards(cards);
      setDrawnCards([]);
      setIsFlipped(false);
      setReadingText('');
      setReadingDone(false);
      setPhase(PHASES.DRAWING);
    } catch (e) {
      setError(e.message || '开始占卜失败，请稍后再试');
    } finally {
      setLoading(false);
    }
  }, []);

  const handleDraw = useCallback(async (cardKey) => {
    if (!sessionId || !spread) return;
    // 已抽满或该牌已抽走则忽略
    const picked = fanCards.find((c) => c.key === cardKey);
    if (!picked || picked.drawn) return;
    if (drawnCards.length >= spread.cardCount) return;

    setFanCards((prev) =>
      prev.map((c) => (c.key === cardKey ? { ...c, drawn: true } : c))
    );

    try {
      const data = await drawCard(sessionId);
      // 后端返回真实牌，带唯一 key 供共享布局动画使用
      const newCard = { ...data.card, key: cardKey };
      setDrawnCards((prev) => [...prev, newCard]);
      if (data.complete) {
        setTimeout(() => {
          setIsFlipped(true);
          setPhase(PHASES.FLIPPING);
        }, 700);
      }
    } catch (e) {
      // 抽卡失败，回滚该牌的已抽状态
      setFanCards((prev) =>
        prev.map((c) => (c.key === cardKey ? { ...c, drawn: false } : c))
      );
      setError(e.message || '抽卡失败');
    }
  }, [sessionId, spread, fanCards, drawnCards]);

  const handleStartReading = useCallback(() => {
    if (!sessionId) return;
    setReadingText('');
    setReadingDone(false);
    setError('');
    setPhase(PHASES.READING);
    interpretStream(sessionId, {
      onChunk: (chunk) => setReadingText((prev) => prev + chunk),
      onDone: () => setReadingDone(true),
      onError: () => {
        setReadingDone(true);
        setError('解读过程中断，请重试');
      },
    });
  }, [sessionId]);

  const handleReset = useCallback(() => {
    setPhase(PHASES.QUESTION);
    setQuestion('');
    setSessionId(null);
    setSpread(null);
    setFanCards([]);
    setDrawnCards([]);
    setIsFlipped(false);
    setReadingText('');
    setReadingDone(false);
    setError('');
  }, []);

  return (
    <>
      <AnimatePresence mode="wait">
        {phase === PHASES.QUESTION && (
          <motion.div
            key="question"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <QuestionInput onSubmit={handleQuestionSubmit} loading={loading} />
            {error && (
              <p style={{ color: '#ff6b6b', textAlign: 'center', marginTop: '1rem' }}>
                {error}
              </p>
            )}
          </motion.div>
        )}

        {(phase === PHASES.DRAWING || phase === PHASES.FLIPPING || phase === PHASES.READING) && (
          <motion.div
            key="main"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            style={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              gap: '1rem',
              width: '100%',
              maxWidth: '900px',
            }}
          >
            {spread && (
              <DrawingBoard
                spread={spread}
                fanCards={fanCards}
                drawnCards={drawnCards}
                isFlipped={isFlipped}
                onDraw={handleDraw}
              />
            )}

            {phase === PHASES.FLIPPING && (
              <motion.button
                onClick={handleStartReading}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: 0.8 }}
                style={{
                  marginTop: '1.5rem',
                  padding: '0.8rem 2rem',
                  fontSize: '1rem',
                  background: 'linear-gradient(135deg, var(--color-accent-purple), var(--color-accent-blue))',
                  color: 'white',
                  borderRadius: 'var(--radius-md)',
                  fontWeight: 600,
                  border: 'none',
                  cursor: 'pointer',
                  letterSpacing: '0.05em',
                }}
              >
                开始解牌
              </motion.button>
            )}

            {phase === PHASES.READING && (
              <ReadingDisplay text={readingText} streaming done={readingDone} />
            )}

            {phase === PHASES.READING && (
              <motion.button
                onClick={handleReset}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ delay: 2 }}
                style={{
                  marginTop: '1rem',
                  padding: '0.6rem 1.5rem',
                  fontSize: '0.9rem',
                  background: 'transparent',
                  color: 'var(--color-text-secondary)',
                  border: '1px solid var(--color-border)',
                  borderRadius: 'var(--radius-md)',
                  cursor: 'pointer',
                }}
              >
                再问一次
              </motion.button>
            )}
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
}
