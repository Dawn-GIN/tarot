import { motion } from 'framer-motion';
import { TarotCard, CardSlot } from './TarotCard';
import styles from './DrawingBoard.module.css';

// 计算弧形排布中每张牌的位置和旋转角度
// 牌多时通过限制每张牌之间的角度间隔来让它们适当重叠，形成扇形牌堆
function getFanTransform(index, total) {
  if (total <= 1) {
    return { x: 0, y: 0, rotate: 0 };
  }
  const perCard = 2.2; // 相邻两张牌之间的角度间隔
  const maxAngle = Math.min(170, perCard * (total - 1)); // 整个扇形张开的总角度
  const step = maxAngle / (total - 1);
  const angle = -maxAngle / 2 + step * index;
  const radius = 520; // 弧形半径（越大弧越平缓）
  const rad = (angle * Math.PI) / 180;
  const x = Math.sin(rad) * radius;
  const y = (1 - Math.cos(rad)) * radius; // 中间高两边低
  return { x, y, rotate: angle };
}

export default function DrawingBoard({
  spread,
  fanCards,
  drawnCards,
  isFlipped,
  onDraw,
}) {
  const totalNeeded = spread.cardCount;
  const drawnCount = drawnCards.length;
  const allDrawn = drawnCount >= totalNeeded;

  return (
    <div className={styles.board}>
      {/* 上方：弧形牌堆 */}
      <div className={styles.fanArea}>
        {fanCards.map((fc, i) => {
          if (fc.drawn) return null; // 已被抽走的不在扇形里显示
          const { x, y, rotate } = getFanTransform(i, fanCards.length);
          return (
            <motion.div
              key={fc.key}
              className={styles.fanCard}
              style={{ zIndex: i }}
              initial={{ x, y, rotate }}
              animate={{ x, y, rotate }}
              whileHover={allDrawn ? {} : { y: y - 20, scale: 1.05, zIndex: 100 }}
              transition={{ type: 'spring', stiffness: 300, damping: 30 }}
            >
              <TarotCard
                small
                layoutId={fc.key}
                onClick={allDrawn ? undefined : () => onDraw(fc.key)}
              />
            </motion.div>
          );
        })}
      </div>

      {/* 提示 */}
      <p className={styles.hint}>
        {allDrawn
          ? '所有卡牌已就位'
          : `点击上方卡牌抽取 · 还需 ${totalNeeded - drawnCount} 张`}
      </p>

      {/* 下方：牌阵卡槽 */}
      <div className={styles.spreadArea}>
        <h2 className={styles.spreadTitle}>{spread.name}</h2>
        <p className={styles.spreadDesc}>{spread.description}</p>
        <div className={styles.slotsRow}>
          {spread.positions.map((position, i) => (
            <div key={position.index} className={styles.slotWrapper}>
              {drawnCards[i] ? (
                <TarotCard
                  card={drawnCards[i]}
                  isFlipped={isFlipped}
                  layoutId={drawnCards[i].key}
                />
              ) : (
                <CardSlot position={position} />
              )}
              {drawnCards[i] && isFlipped && (
                <span className={styles.cardNameLabel}>
                  {drawnCards[i].name}
                  <span
                    className={
                      drawnCards[i].reversed
                        ? styles.cardOrientationReversed
                        : styles.cardOrientation
                    }
                  >
                    {' - '}
                    {drawnCards[i].reversed ? '逆位' : '正位'}
                  </span>
                </span>
              )}
              <span className={styles.slotPositionLabel}>{position.label}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
