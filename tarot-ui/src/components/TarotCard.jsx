import { motion } from 'framer-motion';
import { CARD_BACK } from '../mock/cards';
import styles from './TarotCard.module.css';

// 一张可翻转的塔罗牌。card 为 null 时只显示背面（用于弧形牌堆）。
// layoutId 用于在"弧形牌堆 -> 牌阵卡槽"之间做共享布局平移动画。
export function TarotCard({ card, isFlipped, onClick, layoutId, small = false }) {
  return (
    <motion.div
      className={`${styles.card} ${small ? styles.cardSmall : ''}`}
      onClick={onClick}
      layoutId={layoutId}
    >
      <div className={`${styles.cardInner} ${isFlipped ? styles.flipped : ''}`}>
        <div className={`${styles.cardFace} ${styles.cardBack}`}>
          <img src={CARD_BACK} alt="牌背" className={styles.cardImage} />
        </div>
        <div className={`${styles.cardFace} ${styles.cardFront}`}>
          {card && card.image && (
            <img
              src={card.image}
              alt={card.name}
              className={`${styles.cardImage} ${card.reversed ? styles.reversedImage : ''}`}
            />
          )}
        </div>
      </div>
    </motion.div>
  );
}

export function CardSlot({ position }) {
  return (
    <div className={styles.cardSlot}>
      <span className={styles.slotIndex}>{position.index + 1}</span>
    </div>
  );
}
