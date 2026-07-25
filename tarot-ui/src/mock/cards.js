const majorArcana = [
  { id: 0, name: '愚者', nameEn: 'The Fool', arcana: 'major', image: '/cards/00-fool.jpg' },
  { id: 1, name: '魔术师', nameEn: 'The Magician', arcana: 'major', image: '/cards/01-magician.jpg' },
  { id: 2, name: '女祭司', nameEn: 'The High Priestess', arcana: 'major', image: '/cards/02-high-priestess.jpg' },
  { id: 3, name: '女皇', nameEn: 'The Empress', arcana: 'major', image: '/cards/03-empress.jpg' },
  { id: 4, name: '皇帝', nameEn: 'The Emperor', arcana: 'major', image: '/cards/04-emperor.jpg' },
  { id: 5, name: '教皇', nameEn: 'The Hierophant', arcana: 'major', image: '/cards/05-hierophant.jpg' },
  { id: 6, name: '恋人', nameEn: 'The Lovers', arcana: 'major', image: '/cards/06-lovers.jpg' },
  { id: 7, name: '战车', nameEn: 'The Chariot', arcana: 'major', image: '/cards/07-chariot.jpg' },
  { id: 8, name: '力量', nameEn: 'Strength', arcana: 'major', image: '/cards/08-strength.jpg' },
  { id: 9, name: '隐士', nameEn: 'The Hermit', arcana: 'major', image: '/cards/09-hermit.jpg' },
  { id: 10, name: '命运之轮', nameEn: 'Wheel of Fortune', arcana: 'major', image: '/cards/10-wheel-of-fortune.jpg' },
  { id: 11, name: '正义', nameEn: 'Justice', arcana: 'major', image: '/cards/11-justice.jpg' },
  { id: 12, name: '倒吊人', nameEn: 'The Hanged Man', arcana: 'major', image: '/cards/12-hanged-man.jpg' },
  { id: 13, name: '死神', nameEn: 'Death', arcana: 'major', image: '/cards/13-death.jpg' },
  { id: 14, name: '节制', nameEn: 'Temperance', arcana: 'major', image: '/cards/14-temperance.jpg' },
  { id: 15, name: '恶魔', nameEn: 'The Devil', arcana: 'major', image: '/cards/15-devil.jpg' },
  { id: 16, name: '塔', nameEn: 'The Tower', arcana: 'major', image: '/cards/16-tower.jpg' },
  { id: 17, name: '星星', nameEn: 'The Star', arcana: 'major', image: '/cards/17-star.jpg' },
  { id: 18, name: '月亮', nameEn: 'The Moon', arcana: 'major', image: '/cards/18-moon.jpg' },
  { id: 19, name: '太阳', nameEn: 'The Sun', arcana: 'major', image: '/cards/19-sun.jpg' },
  { id: 20, name: '审判', nameEn: 'Judgement', arcana: 'major', image: '/cards/20-judgement.jpg' },
  { id: 21, name: '世界', nameEn: 'The World', arcana: 'major', image: '/cards/21-world.jpg' },
];

const suits = ['wands', 'cups', 'swords', 'pentacles'];
const suitNames = { wands: '权杖', cups: '圣杯', swords: '宝剑', pentacles: '星币' };
const ranks = [
  { rank: 'ace', name: '一' },
  { rank: '2', name: '二' },
  { rank: '3', name: '三' },
  { rank: '4', name: '四' },
  { rank: '5', name: '五' },
  { rank: '6', name: '六' },
  { rank: '7', name: '七' },
  { rank: '8', name: '八' },
  { rank: '9', name: '九' },
  { rank: '10', name: '十' },
  { rank: 'page', name: '侍从' },
  { rank: 'knight', name: '骑士' },
  { rank: 'queen', name: '王后' },
  { rank: 'king', name: '国王' },
];

const minorArcana = [];
let id = 22;
for (const suit of suits) {
  for (const { rank, name } of ranks) {
    const numStr = String(id).padStart(2, '0');
    minorArcana.push({
      id: id,
      name: `${suitNames[suit]}${name}`,
      nameEn: `${rank.charAt(0).toUpperCase() + rank.slice(1)} of ${suit.charAt(0).toUpperCase() + suit.slice(1)}`,
      suit,
      rank,
      arcana: 'minor',
      image: `/cards/${numStr}-${suit}-${rank}.jpg`,
    });
    id++;
  }
}

export const allCards = [...majorArcana, ...minorArcana];

export const CARD_BACK = '/cards/back.jpg';

export function drawRandomCards(count) {
  const shuffled = [...allCards].sort(() => Math.random() - 0.5);
  return shuffled.slice(0, count).map(card => ({
    ...card,
    reversed: Math.random() > 0.7,
  }));
}
