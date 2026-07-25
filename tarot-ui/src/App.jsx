import { useState, useCallback } from 'react';
import { AnimatePresence, motion } from 'framer-motion';
import QuestionInput from './components/QuestionInput';
import DrawingBoard from './components/DrawingBoard';
import ReadingDisplay from './components/ReadingDisplay';
import { selectSpread } from './mock/spreads';
import { drawRandomCards } from './mock/cards';
import { generateReading } from './mock/readings';

// 弧形牌堆里展示的候选牌数量（78 = 完整牌组）
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
  const [question, setQuestion] = useState('');
  const [spread, setSpread] = useState(null);
  const [fanCards, setFanCards] = useState([]);
  const [drawnCards, setDrawnCards] = useState([]);
  const [isFlipped, setIsFlipped] = useState(false);
  const [readingText, setReadingText] = useState('');

  const handleQuestionSubmit = useCallback((q) => {
    setQuestion(q);
    const selectedSpread = selectSpread(q);
    setSpread(selectedSpread);
    // 准备一批弧形候选牌，每张带唯一 key 和预设的正逆位
    const cards = drawRandomCards(FAN_SIZE).map((card, i) => ({
      ...card,
      key: `fan-${i}-${card.id}`,
      drawn: false,
    }));
    setFanCards(cards);
    setDrawnCards([]);
    setIsFlipped(false);
    setReadingText('');
    setPhase(PHASES.DRAWING);
  }, []);

  const handleDraw = useCallback((cardKey) => {
    setFanCards((prevFan) => {
      const picked = prevFan.find((c) => c.key === cardKey);
      if (!picked || picked.drawn) return prevFan;

      setDrawnCards((prevDrawn) => {
        if (prevDrawn.length >= spread.cardCount) return prevDrawn;
        const newDrawn = [...prevDrawn, picked];
        if (newDrawn.length === spread.cardCount) {
          // 全部抽完，稍后统一翻牌
          setTimeout(() => {
            setIsFlipped(true);
            setPhase(PHASES.FLIPPING);
          }, 700);
        }
        return newDrawn;
      });

      return prevFan.map((c) =>
        c.key === cardKey ? { ...c, drawn: true } : c
      );
    });
  }, [spread]);

  const handleStartReading = useCallback(() => {
    const text = generateReading(drawnCards, spread.positions);
    setReadingText(text);
    setPhase(PHASES.READING);
  }, [drawnCards, spread]);

  const handleReset = useCallback(() => {
    setPhase(PHASES.QUESTION);
    setQuestion('');
    setSpread(null);
    setFanCards([]);
    setDrawnCards([]);
    setIsFlipped(false);
    setReadingText('');
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
            <QuestionInput onSubmit={handleQuestionSubmit} />
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
              <ReadingDisplay text={readingText} />
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
