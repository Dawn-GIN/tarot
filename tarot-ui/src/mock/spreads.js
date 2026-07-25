export const spreads = [
  {
    id: 'single',
    name: '单张牌阵',
    description: '简单直接的回答',
    cardCount: 1,
    positions: [
      { index: 0, label: '启示', description: '针对你的问题的核心启示' },
    ],
  },
  {
    id: 'three-card',
    name: '三张牌阵',
    description: '过去、现在、未来的时间线解读',
    cardCount: 3,
    positions: [
      { index: 0, label: '过去', description: '影响当前局面的过往因素' },
      { index: 1, label: '现在', description: '你目前所处的状态' },
      { index: 2, label: '未来', description: '事态发展的趋势' },
    ],
  },
  {
    id: 'celtic-cross',
    name: '凯尔特十字',
    description: '全面深入的综合分析',
    cardCount: 6,
    positions: [
      { index: 0, label: '现状', description: '当前核心状况' },
      { index: 1, label: '挑战', description: '面临的主要障碍' },
      { index: 2, label: '潜意识', description: '内心深处的想法' },
      { index: 3, label: '过去', description: '近期影响事件' },
      { index: 4, label: '可能', description: '最佳可能结果' },
      { index: 5, label: '未来', description: '即将到来的影响' },
    ],
  },
];

export function selectSpread(question) {
  const len = question.length;
  if (len <= 10) return spreads[0];
  if (len <= 30) return spreads[1];
  return spreads[2];
}
