import { motion } from 'framer-motion';
import useTypewriter from '../hooks/useTypewriter';
import styles from './ReadingDisplay.module.css';

export default function ReadingDisplay({ text, onComplete }) {
  const { displayed, isComplete } = useTypewriter(text, 25);

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
