const readingTemplates = [
  {
    intro: '让我为你解读这次占卜的启示...\n\n',
    body: '从牌面的整体能量来看，{cards}呈现出一种{theme}的氛围。这暗示着你正处于一个{phase}的阶段。\n\n{position_readings}\n\n',
    conclusion: '综合来看，宇宙正在引导你走向内心真正渴望的方向。关键是要相信自己的直觉，同时保持对未知的开放态度。记住，塔罗牌展示的是可能性，而非命运——最终的选择权始终在你手中。',
  },
  {
    intro: '我感受到了这些牌面传递的深层信息...\n\n',
    body: '牌阵中的能量流动显示，{cards}共同编织出一幅{theme}的图景。你当前的处境与{phase}密切相关。\n\n{position_readings}\n\n',
    conclusion: '这次解读的核心信息是：变化是成长的催化剂。不要抗拒正在发生的转变，它们都是通向更好自己的必经之路。保持耐心，答案会在适当的时候显现。',
  },
  {
    intro: '牌面已经揭示了它们的秘密...\n\n',
    body: '在这个牌阵中，{cards}组合在一起，传递着关于{theme}的讯息。这与你生命中{phase}的能量共振。\n\n{position_readings}\n\n',
    conclusion: '最后我想说的是：每一张牌都是一面镜子，映照的是你内心已经知道但尚未承认的真相。信任这个过程，允许自己去感受、去探索、去成长。宇宙总是站在你这一边的。',
  },
];

const themes = ['深刻转变', '内在觉醒', '平衡与和谐', '勇气与突破', '智慧与洞察'];
const phases = ['自我发现', '重要转折', '积蓄力量', '收获成果', '放下过去'];

const positionReadingPhrases = {
  reversed: [
    '此牌以逆位出现，暗示着内在的阻力或尚未觉察的盲点。',
    '逆位的能量提示你需要重新审视这个领域的某些信念。',
    '这张逆位牌提醒你，有些事情需要先在内心层面解决。',
  ],
  upright: [
    '正位的能量流动顺畅，表明这个方面正处于积极发展中。',
    '此牌以正位呈现，展示出清晰而有力的指引。',
    '正位的力量支持着你，在这个领域你走在正确的道路上。',
  ],
};

function generatePositionReading(card, position) {
  const orientation = card.reversed ? 'reversed' : 'upright';
  const phrases = positionReadingPhrases[orientation];
  const phrase = phrases[Math.floor(Math.random() * phrases.length)];
  const cardDisplay = card.reversed ? `${card.name}（逆位）` : `${card.name}（正位）`;
  return `【${position.label}】— ${cardDisplay}\n${phrase} 在"${position.label}"的位置上，${card.name}的能量指向${position.description}。这意味着你需要关注这个层面的变化与成长。\n`;
}

export function generateReading(cards, positions) {
  const template = readingTemplates[Math.floor(Math.random() * readingTemplates.length)];
  const theme = themes[Math.floor(Math.random() * themes.length)];
  const phase = phases[Math.floor(Math.random() * phases.length)];

  const cardNames = cards.map(c => c.reversed ? `${c.name}（逆位）` : `${c.name}（正位）`).join('、');

  const positionReadings = cards
    .map((card, i) => generatePositionReading(card, positions[i]))
    .join('\n');

  let text = template.intro;
  text += template.body
    .replace('{cards}', cardNames)
    .replace('{theme}', theme)
    .replace('{phase}', phase)
    .replace('{position_readings}', positionReadings);
  text += template.conclusion;

  return text;
}
