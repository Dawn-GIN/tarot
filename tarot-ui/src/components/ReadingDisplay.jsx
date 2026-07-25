import { motion } from 'framer-motion';
import useTypewriter from '../hooks/useTypewriter';
import styles from './ReadingDisplay.module.css';

export default function ReadingDisplay({ text, streaming = false, done = false }) {
  // 流式模式:文本由外部逐步追加,直接展示;非流式:退回打字机效果
  const typewriter = useTypewriter(streaming ? '' : text, 25);
  const displayed = streaming ? text : typewriter.displayed;
  const isComplete = streaming ? done : typewriter.isComplete;

  return (
    <motion.div
      className={styles.readingContainer}
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className={styles.readingText}>
        {displayed}
        {!isComplete && <span className={styles.cursor} />}
      </div>
      {isComplete && (
        <motion.p
          className={styles.completeHint}
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5 }}
        >
          解读完成 — 愿这些启示能为你指引方向
        </motion.p>
      )}
    </motion.div>
  );
}
