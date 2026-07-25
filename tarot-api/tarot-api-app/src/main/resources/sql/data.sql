-- 牌型初始数据(对齐前端 mock/spreads.js)
INSERT INTO spread (code, name, description, card_count, positions) VALUES
('single', '单张牌阵', '简单直接的回答', 1,
 '[{"index":0,"label":"启示","description":"针对你的问题的核心启示"}]'),
('three-card', '三张牌阵', '过去、现在、未来的时间线解读', 3,
 '[{"index":0,"label":"过去","description":"影响当前局面的过往因素"},{"index":1,"label":"现在","description":"你目前所处的状态"},{"index":2,"label":"未来","description":"事态发展的趋势"}]'),
('celtic-cross', '凯尔特十字', '全面深入的综合分析', 6,
 '[{"index":0,"label":"现状","description":"当前核心状况"},{"index":1,"label":"挑战","description":"面临的主要障碍"},{"index":2,"label":"潜意识","description":"内心深处的想法"},{"index":3,"label":"过去","description":"近期影响事件"},{"index":4,"label":"可能","description":"最佳可能结果"},{"index":5,"label":"未来","description":"即将到来的影响"}]')
AS new
ON DUPLICATE KEY UPDATE name=new.name, description=new.description, card_count=new.card_count, positions=new.positions;

-- 大阿卡纳 22 张(对齐前端 mock/cards.js)
INSERT INTO card (id, name, name_en, arcana, suit, `rank`, image) VALUES
(0,  '愚者',   'The Fool',            'major', NULL, NULL, '/cards/00-fool.jpg'),
(1,  '魔术师', 'The Magician',        'major', NULL, NULL, '/cards/01-magician.jpg'),
(2,  '女祭司', 'The High Priestess',  'major', NULL, NULL, '/cards/02-high-priestess.jpg'),
(3,  '女皇',   'The Empress',         'major', NULL, NULL, '/cards/03-empress.jpg'),
(4,  '皇帝',   'The Emperor',         'major', NULL, NULL, '/cards/04-emperor.jpg'),
(5,  '教皇',   'The Hierophant',      'major', NULL, NULL, '/cards/05-hierophant.jpg'),
(6,  '恋人',   'The Lovers',          'major', NULL, NULL, '/cards/06-lovers.jpg'),
(7,  '战车',   'The Chariot',         'major', NULL, NULL, '/cards/07-chariot.jpg'),
(8,  '力量',   'Strength',            'major', NULL, NULL, '/cards/08-strength.jpg'),
(9,  '隐士',   'The Hermit',          'major', NULL, NULL, '/cards/09-hermit.jpg'),
(10, '命运之轮','Wheel of Fortune',   'major', NULL, NULL, '/cards/10-wheel-of-fortune.jpg'),
(11, '正义',   'Justice',             'major', NULL, NULL, '/cards/11-justice.jpg'),
(12, '倒吊人', 'The Hanged Man',      'major', NULL, NULL, '/cards/12-hanged-man.jpg'),
(13, '死神',   'Death',               'major', NULL, NULL, '/cards/13-death.jpg'),
(14, '节制',   'Temperance',          'major', NULL, NULL, '/cards/14-temperance.jpg'),
(15, '恶魔',   'The Devil',           'major', NULL, NULL, '/cards/15-devil.jpg'),
(16, '塔',     'The Tower',           'major', NULL, NULL, '/cards/16-tower.jpg'),
(17, '星星',   'The Star',            'major', NULL, NULL, '/cards/17-star.jpg'),
(18, '月亮',   'The Moon',            'major', NULL, NULL, '/cards/18-moon.jpg'),
(19, '太阳',   'The Sun',             'major', NULL, NULL, '/cards/19-sun.jpg'),
(20, '审判',   'Judgement',           'major', NULL, NULL, '/cards/20-judgement.jpg'),
(21, '世界',   'The World',           'major', NULL, NULL, '/cards/21-world.jpg')
AS new ON DUPLICATE KEY UPDATE name=new.name;

-- 小阿卡纳 - 权杖 wands (22-35)
INSERT INTO card (id, name, name_en, arcana, suit, `rank`, image) VALUES
(22, '权杖一', 'Ace of Wands',   'minor', 'wands', 'ace',   '/cards/22-wands-ace.jpg'),
(23, '权杖二', '2 of Wands',     'minor', 'wands', '2',     '/cards/23-wands-2.jpg'),
(24, '权杖三', '3 of Wands',     'minor', 'wands', '3',     '/cards/24-wands-3.jpg'),
(25, '权杖四', '4 of Wands',     'minor', 'wands', '4',     '/cards/25-wands-4.jpg'),
(26, '权杖五', '5 of Wands',     'minor', 'wands', '5',     '/cards/26-wands-5.jpg'),
(27, '权杖六', '6 of Wands',     'minor', 'wands', '6',     '/cards/27-wands-6.jpg'),
(28, '权杖七', '7 of Wands',     'minor', 'wands', '7',     '/cards/28-wands-7.jpg'),
(29, '权杖八', '8 of Wands',     'minor', 'wands', '8',     '/cards/29-wands-8.jpg'),
(30, '权杖九', '9 of Wands',     'minor', 'wands', '9',     '/cards/30-wands-9.jpg'),
(31, '权杖十', '10 of Wands',    'minor', 'wands', '10',    '/cards/31-wands-10.jpg'),
(32, '权杖侍从', 'Page of Wands', 'minor', 'wands', 'page',  '/cards/32-wands-page.jpg'),
(33, '权杖骑士', 'Knight of Wands','minor', 'wands', 'knight','/cards/33-wands-knight.jpg'),
(34, '权杖王后', 'Queen of Wands','minor', 'wands', 'queen', '/cards/34-wands-queen.jpg'),
(35, '权杖国王', 'King of Wands', 'minor', 'wands', 'king',  '/cards/35-wands-king.jpg')
AS new ON DUPLICATE KEY UPDATE name=new.name;

-- 小阿卡纳 - 圣杯 cups (36-49)
INSERT INTO card (id, name, name_en, arcana, suit, `rank`, image) VALUES
(36, '圣杯一', 'Ace of Cups',    'minor', 'cups', 'ace',   '/cards/36-cups-ace.jpg'),
(37, '圣杯二', '2 of Cups',      'minor', 'cups', '2',     '/cards/37-cups-2.jpg'),
(38, '圣杯三', '3 of Cups',      'minor', 'cups', '3',     '/cards/38-cups-3.jpg'),
(39, '圣杯四', '4 of Cups',      'minor', 'cups', '4',     '/cards/39-cups-4.jpg'),
(40, '圣杯五', '5 of Cups',      'minor', 'cups', '5',     '/cards/40-cups-5.jpg'),
(41, '圣杯六', '6 of Cups',      'minor', 'cups', '6',     '/cards/41-cups-6.jpg'),
(42, '圣杯七', '7 of Cups',      'minor', 'cups', '7',     '/cards/42-cups-7.jpg'),
(43, '圣杯八', '8 of Cups',      'minor', 'cups', '8',     '/cards/43-cups-8.jpg'),
(44, '圣杯九', '9 of Cups',      'minor', 'cups', '9',     '/cards/44-cups-9.jpg'),
(45, '圣杯十', '10 of Cups',     'minor', 'cups', '10',    '/cards/45-cups-10.jpg'),
(46, '圣杯侍从', 'Page of Cups', 'minor', 'cups', 'page',  '/cards/46-cups-page.jpg'),
(47, '圣杯骑士', 'Knight of Cups','minor', 'cups', 'knight','/cards/47-cups-knight.jpg'),
(48, '圣杯王后', 'Queen of Cups','minor', 'cups', 'queen', '/cards/48-cups-queen.jpg'),
(49, '圣杯国王', 'King of Cups', 'minor', 'cups', 'king',  '/cards/49-cups-king.jpg')
AS new ON DUPLICATE KEY UPDATE name=new.name;

-- 小阿卡纳 - 宝剑 swords (50-63)
INSERT INTO card (id, name, name_en, arcana, suit, `rank`, image) VALUES
(50, '宝剑一', 'Ace of Swords',  'minor', 'swords', 'ace',   '/cards/50-swords-ace.jpg'),
(51, '宝剑二', '2 of Swords',    'minor', 'swords', '2',     '/cards/51-swords-2.jpg'),
(52, '宝剑三', '3 of Swords',    'minor', 'swords', '3',     '/cards/52-swords-3.jpg'),
(53, '宝剑四', '4 of Swords',    'minor', 'swords', '4',     '/cards/53-swords-4.jpg'),
(54, '宝剑五', '5 of Swords',    'minor', 'swords', '5',     '/cards/54-swords-5.jpg'),
(55, '宝剑六', '6 of Swords',    'minor', 'swords', '6',     '/cards/55-swords-6.jpg'),
(56, '宝剑七', '7 of Swords',    'minor', 'swords', '7',     '/cards/56-swords-7.jpg'),
(57, '宝剑八', '8 of Swords',    'minor', 'swords', '8',     '/cards/57-swords-8.jpg'),
(58, '宝剑九', '9 of Swords',    'minor', 'swords', '9',     '/cards/58-swords-9.jpg'),
(59, '宝剑十', '10 of Swords',   'minor', 'swords', '10',    '/cards/59-swords-10.jpg'),
(60, '宝剑侍从', 'Page of Swords','minor', 'swords', 'page',  '/cards/60-swords-page.jpg'),
(61, '宝剑骑士', 'Knight of Swords','minor','swords', 'knight','/cards/61-swords-knight.jpg'),
(62, '宝剑王后', 'Queen of Swords','minor','swords', 'queen', '/cards/62-swords-queen.jpg'),
(63, '宝剑国王', 'King of Swords','minor', 'swords', 'king',  '/cards/63-swords-king.jpg')
AS new ON DUPLICATE KEY UPDATE name=new.name;

-- 小阿卡纳 - 星币 pentacles (64-77)
INSERT INTO card (id, name, name_en, arcana, suit, `rank`, image) VALUES
(64, '星币一', 'Ace of Pentacles','minor', 'pentacles', 'ace',  '/cards/64-pentacles-ace.jpg'),
(65, '星币二', '2 of Pentacles', 'minor', 'pentacles', '2',    '/cards/65-pentacles-2.jpg'),
(66, '星币三', '3 of Pentacles', 'minor', 'pentacles', '3',    '/cards/66-pentacles-3.jpg'),
(67, '星币四', '4 of Pentacles', 'minor', 'pentacles', '4',    '/cards/67-pentacles-4.jpg'),
(68, '星币五', '5 of Pentacles', 'minor', 'pentacles', '5',    '/cards/68-pentacles-5.jpg'),
(69, '星币六', '6 of Pentacles', 'minor', 'pentacles', '6',    '/cards/69-pentacles-6.jpg'),
(70, '星币七', '7 of Pentacles', 'minor', 'pentacles', '7',    '/cards/70-pentacles-7.jpg'),
(71, '星币八', '8 of Pentacles', 'minor', 'pentacles', '8',    '/cards/71-pentacles-8.jpg'),
(72, '星币九', '9 of Pentacles', 'minor', 'pentacles', '9',    '/cards/72-pentacles-9.jpg'),
(73, '星币十', '10 of Pentacles','minor', 'pentacles', '10',   '/cards/73-pentacles-10.jpg'),
(74, '星币侍从', 'Page of Pentacles','minor','pentacles','page', '/cards/74-pentacles-page.jpg'),
(75, '星币骑士', 'Knight of Pentacles','minor','pentacles','knight','/cards/75-pentacles-knight.jpg'),
(76, '星币王后', 'Queen of Pentacles','minor','pentacles','queen','/cards/76-pentacles-queen.jpg'),
(77, '星币国王', 'King of Pentacles','minor','pentacles','king', '/cards/77-pentacles-king.jpg')
AS new ON DUPLICATE KEY UPDATE name=new.name;
