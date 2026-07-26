import { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { getCard } from '../api/tarot';
import styles from './CardDetailModal.module.css';

export default function CardDetailModal({ card, onClose }) {
  const [detail, setDetail] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!card) return;
    setDetail(null);
    setError('');
    getCard(card.id)
      .then(setDetail)
      .catch((e) => setError(e.message || '加载失败'));
  }, [card]);

  const reversed = card?.reversed;

  return (
    <AnimatePresence>
      {card && (
        <motion.div
          className={styles.overlay}
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          onClick={onClose}
        >
          <motion.div
            className={styles.modal}
            initial={{ scale: 0.9, y: 20 }}
            animate={{ scale: 1, y: 0 }}
            exit={{ scale: 0.9, opacity: 0 }}
            onClick={(e) => e.stopPropagation()}
          >
            <button className={styles.closeBtn} onClick={onClose}>×</button>

            <div className={styles.header}>
              <img
                src={card.image}
                alt={card.name}
                className={`${styles.cardImage} ${reversed ? styles.reversed : ''}`}
              />
              <div className={styles.titleArea}>
                <h3 className={styles.cardName}>{card.name}</h3>
                <span className={styles.orientation}>
                  {reversed ? '逆位' : '正位'}
                </span>
              </div>
            </div>

            {error && <p className={styles.error}>{error}</p>}
            {!detail && !error && <p className={styles.loading}>加载中...</p>}

            {detail && (
              <div className={styles.meanings}>
                <div className={`${styles.meaningBlock} ${!reversed ? styles.active : ''}`}>
                  <span className={styles.meaningLabel}>正位</span>
                  <p className={styles.meaningText}>{detail.uprightMeaning || '暂无'}</p>
                </div>
                <div className={`${styles.meaningBlock} ${reversed ? styles.active : ''}`}>
                  <span className={styles.meaningLabel}>逆位</span>
                  <p className={styles.meaningText}>{detail.reversedMeaning || '暂无'}</p>
                </div>
              </div>
            )}
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
}
