import { useState } from 'react';
import { motion } from 'framer-motion';
import styles from './QuestionInput.module.css';

export default function QuestionInput({ onSubmit }) {
  const [question, setQuestion] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (question.trim()) {
      onSubmit(question.trim());
    }
  };

  return (
    <motion.div
      className={styles.questionContainer}
      initial={{ opacity: 0, y: 30 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.8, ease: 'easeOut' }}
    >
      <h1 className={styles.title}>AI 塔罗</h1>
      <p className={styles.subtitle}>向宇宙提出你的问题，让牌面揭示答案</p>
      <form className={styles.inputWrapper} onSubmit={handleSubmit}>
        <textarea
          className={styles.input}
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="请输入你想问的问题..."
          rows={3}
        />
      </form>
      <button
        className={styles.submitBtn}
        onClick={handleSubmit}
        disabled={!question.trim()}
      >
        开始占卜
      </button>
    </motion.div>
  );
}
