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

-- 正逆位含义(大阿卡纳 0-13)
UPDATE card SET upright_meaning='新的开始、冒险、自由、纯真与无限可能', reversed_meaning='鲁莽、盲目冒进、逃避责任、准备不足' WHERE id=0;
UPDATE card SET upright_meaning='创造力、行动力、资源整合、专注意志', reversed_meaning='欺骗、能力未发挥、操纵、缺乏规划' WHERE id=1;
UPDATE card SET upright_meaning='直觉、潜意识、神秘、内在智慧', reversed_meaning='忽视直觉、秘密被掩藏、表里不一' WHERE id=2;
UPDATE card SET upright_meaning='丰饶、母性、创造、滋养与富足', reversed_meaning='依赖、创造力受阻、过度保护' WHERE id=3;
UPDATE card SET upright_meaning='权威、秩序、稳定、领导力', reversed_meaning='专制、僵化、控制欲过强' WHERE id=4;
UPDATE card SET upright_meaning='传统、信仰、指引、规范', reversed_meaning='墨守成规、教条、盲目反叛' WHERE id=5;
UPDATE card SET upright_meaning='爱情、结合、和谐、重要抉择', reversed_meaning='关系失衡、错误选择、诱惑' WHERE id=6;
UPDATE card SET upright_meaning='意志、掌控、胜利、勇往直前', reversed_meaning='失控、方向迷失、鲁莽冲动' WHERE id=7;
UPDATE card SET upright_meaning='内在力量、勇气、耐心、柔韧', reversed_meaning='自我怀疑、软弱、情绪失控' WHERE id=8;
UPDATE card SET upright_meaning='内省、独处、寻求真理、指引', reversed_meaning='孤立、逃避、固执己见' WHERE id=9;
UPDATE card SET upright_meaning='转机、循环、命运、机遇来临', reversed_meaning='厄运、失控、抗拒变化' WHERE id=10;
UPDATE card SET upright_meaning='公正、平衡、真相、责任', reversed_meaning='不公、逃避责任、偏见' WHERE id=11;
UPDATE card SET upright_meaning='牺牲、换位思考、暂停、放下', reversed_meaning='无谓牺牲、停滞、拖延' WHERE id=12;
UPDATE card SET upright_meaning='结束、转变、重生、放下过去', reversed_meaning='抗拒改变、停滞、恐惧终结' WHERE id=13;

-- 正逆位含义(大阿卡纳 14-21)
UPDATE card SET upright_meaning='节制、平衡、调和、耐心', reversed_meaning='失衡、极端、缺乏耐心' WHERE id=14;
UPDATE card SET upright_meaning='欲望、束缚、诱惑、物质执着', reversed_meaning='挣脱束缚、觉醒、摆脱依赖' WHERE id=15;
UPDATE card SET upright_meaning='突变、崩塌、觉醒、旧结构瓦解', reversed_meaning='逃避灾难、拖延崩溃、内在动荡' WHERE id=16;
UPDATE card SET upright_meaning='希望、灵感、疗愈、指引之光', reversed_meaning='失望、信心动摇、迷失方向' WHERE id=17;
UPDATE card SET upright_meaning='潜意识、幻象、不安、直觉', reversed_meaning='迷惑消散、真相显现、释放恐惧' WHERE id=18;
UPDATE card SET upright_meaning='喜悦、成功、活力、光明', reversed_meaning='暂时受挫、过度乐观、光芒被遮' WHERE id=19;
UPDATE card SET upright_meaning='觉醒、审判、重生、召唤', reversed_meaning='自我怀疑、逃避、错失召唤' WHERE id=20;
UPDATE card SET upright_meaning='圆满、完成、整合、成就', reversed_meaning='未竟之业、拖延、缺乏收尾' WHERE id=21;

-- 正逆位含义(权杖 22-35)
UPDATE card SET upright_meaning='灵感、新契机、热情、行动的火花', reversed_meaning='动力不足、计划受阻、犹豫' WHERE id=22;
UPDATE card SET upright_meaning='规划、远见、抉择、掌控未来', reversed_meaning='缺乏计划、恐惧未知、优柔寡断' WHERE id=23;
UPDATE card SET upright_meaning='扩展、进展、远景、把握机会', reversed_meaning='延误、计划落空、目光短浅' WHERE id=24;
UPDATE card SET upright_meaning='庆祝、稳定、和谐、里程碑', reversed_meaning='家庭不和、根基不稳、过渡期' WHERE id=25;
UPDATE card SET upright_meaning='竞争、冲突、挑战、观点碰撞', reversed_meaning='逃避冲突、内耗、化解争端' WHERE id=26;
UPDATE card SET upright_meaning='坚守、防御、勇气、脱颖而出', reversed_meaning='不堪重负、退缩、力不从心' WHERE id=27;
UPDATE card SET upright_meaning='迅速、行动、消息传来、进展神速', reversed_meaning='延误、混乱、操之过急' WHERE id=28;
UPDATE card SET upright_meaning='坚持、韧性、防卫、最后关头', reversed_meaning='精疲力竭、固执、防线崩溃' WHERE id=29;
UPDATE card SET upright_meaning='重负、责任、坚持到底、压力', reversed_meaning='不堪重负、放下负担、逃避' WHERE id=30;
UPDATE card SET upright_meaning='热情的信使、好奇、探索、新消息', reversed_meaning='坏消息、三分钟热度、方向不明' WHERE id=31;
UPDATE card SET upright_meaning='冒险、行动力、热情、勇往直前', reversed_meaning='鲁莽、急躁、半途而废' WHERE id=32;
UPDATE card SET upright_meaning='魅力、自信、领导、热情洋溢', reversed_meaning='专横、急躁、缺乏耐心' WHERE id=33;
UPDATE card SET upright_meaning='自信、独立、活力、感染力', reversed_meaning='嫉妒、易怒、自我中心' WHERE id=34;
UPDATE card SET upright_meaning='远见、领导力、魄力、鼓舞人心', reversed_meaning='专断、冲动、缺乏包容' WHERE id=35;

-- 正逆位含义(圣杯 36-49)
UPDATE card SET upright_meaning='新感情、爱的萌芽、情感充盈、灵性', reversed_meaning='情感压抑、空虚、爱的阻塞' WHERE id=36;
UPDATE card SET upright_meaning='结合、伙伴、相互吸引、和谐关系', reversed_meaning='关系失衡、分歧、缘分渐远' WHERE id=37;
UPDATE card SET upright_meaning='友谊、庆祝、团聚、共享喜悦', reversed_meaning='过度放纵、八卦、社交疲惫' WHERE id=38;
UPDATE card SET upright_meaning='冷漠、沉思、错失、重新评估', reversed_meaning='重燃热情、把握机会、走出停滞' WHERE id=39;
UPDATE card SET upright_meaning='失落、遗憾、哀伤、执着于损失', reversed_meaning='走出悲伤、接纳、看见剩余的美好' WHERE id=40;
UPDATE card SET upright_meaning='回忆、怀旧、纯真、旧友重逢', reversed_meaning='沉溺过去、无法向前、告别童真' WHERE id=41;
UPDATE card SET upright_meaning='幻想、选择、白日梦、可能性', reversed_meaning='看清现实、抉择、摆脱空想' WHERE id=42;
UPDATE card SET upright_meaning='离开、追寻、放下、寻找更深意义', reversed_meaning='逃避、原地徘徊、害怕改变' WHERE id=43;
UPDATE card SET upright_meaning='满足、愿望成真、幸福、如愿以偿', reversed_meaning='贪求、虚荣、内在不满足' WHERE id=44;
UPDATE card SET upright_meaning='圆满、家庭幸福、情感和谐、喜悦', reversed_meaning='关系失和、理想破灭、表面和睦' WHERE id=45;
UPDATE card SET upright_meaning='浪漫的信使、敏感、创意、好消息', reversed_meaning='情绪化、逃避现实、坏消息' WHERE id=46;
UPDATE card SET upright_meaning='浪漫、追求、理想主义、真情表白', reversed_meaning='不切实际、善变、情感欺骗' WHERE id=47;
UPDATE card SET upright_meaning='同理心、温柔、直觉、情感包容', reversed_meaning='情绪依赖、易受伤、界限模糊' WHERE id=48;
UPDATE card SET upright_meaning='情感成熟、包容、平和、智慧', reversed_meaning='情绪压抑、喜怒无常、操控' WHERE id=49;

-- 正逆位含义(宝剑 50-63)
UPDATE card SET upright_meaning='突破、清晰、真相、思维的力量', reversed_meaning='混乱、误判、滥用力量' WHERE id=50;
UPDATE card SET upright_meaning='僵局、回避、艰难抉择、内心权衡', reversed_meaning='犹豫破解、直面选择、信息明朗' WHERE id=51;
UPDATE card SET upright_meaning='心碎、悲伤、痛苦、情感创伤', reversed_meaning='走出伤痛、宽恕、疗愈开始' WHERE id=52;
UPDATE card SET upright_meaning='休整、恢复、沉淀、暂时退隐', reversed_meaning='重新出发、倦怠、被迫停歇' WHERE id=53;
UPDATE card SET upright_meaning='冲突、争胜、失和、自我利益', reversed_meaning='和解、放下争执、认清代价' WHERE id=54;
UPDATE card SET upright_meaning='过渡、离开、疗愈之旅、走向平静', reversed_meaning='滞留困境、抗拒改变、旧痛未消' WHERE id=55;
UPDATE card SET upright_meaning='策略、谋略、独行、暗中行事', reversed_meaning='阴谋败露、良心不安、坦白' WHERE id=56;
UPDATE card SET upright_meaning='受限、自我设限、困惑、无力感', reversed_meaning='解除束缚、看清真相、重获自由' WHERE id=57;
UPDATE card SET upright_meaning='焦虑、恐惧、忧思、噩梦', reversed_meaning='走出焦虑、释放担忧、希望重现' WHERE id=58;
UPDATE card SET upright_meaning='崩溃、终结、痛苦谷底、彻底放下', reversed_meaning='触底反弹、复苏、走出低谷' WHERE id=59;
UPDATE card SET upright_meaning='机敏的信使、警觉、好奇、洞察', reversed_meaning='多疑、言语伤人、信息混乱' WHERE id=60;
UPDATE card SET upright_meaning='果断、直率、勇往直前、行动迅速', reversed_meaning='鲁莽、咄咄逼人、方向失控' WHERE id=61;
UPDATE card SET upright_meaning='理性、独立、洞察、清晰判断', reversed_meaning='冷漠、苛刻、情感疏离' WHERE id=62;
UPDATE card SET upright_meaning='理智、权威、公正、思维缜密', reversed_meaning='专制、冷酷、滥用权力' WHERE id=63;

-- 正逆位含义(星币 64-77)
UPDATE card SET upright_meaning='新机遇、财富萌芽、务实起点、丰盛', reversed_meaning='机会流失、财务隐忧、计划落空' WHERE id=64;
UPDATE card SET upright_meaning='平衡、灵活、权衡取舍、多线兼顾', reversed_meaning='失衡、力不从心、财务混乱' WHERE id=65;
UPDATE card SET upright_meaning='协作、技艺、学习成长、团队认可', reversed_meaning='缺乏配合、敷衍、技艺不精' WHERE id=66;
UPDATE card SET upright_meaning='守成、稳固、掌控、珍视所有', reversed_meaning='吝啬、过度执着、患得患失' WHERE id=67;
UPDATE card SET upright_meaning='困顿、匮乏、失落、暂时的艰难', reversed_meaning='走出困境、恢复、寻得援手' WHERE id=68;
UPDATE card SET upright_meaning='慷慨、给予、分享、公平回报', reversed_meaning='施舍附带条件、债务、不平等' WHERE id=69;
UPDATE card SET upright_meaning='耐心、评估、长期投入、静待收获', reversed_meaning='急于求成、投入无果、焦虑' WHERE id=70;
UPDATE card SET upright_meaning='专注、精进、技艺打磨、勤勉', reversed_meaning='得过且过、完美主义、缺乏专注' WHERE id=71;
UPDATE card SET upright_meaning='富足、独立、自我实现、优雅从容', reversed_meaning='依赖、物质空虚、透支享乐' WHERE id=72;
UPDATE card SET upright_meaning='财富传承、家族、稳定、长久富足', reversed_meaning='家庭财务纠纷、根基动摇、短视' WHERE id=73;
UPDATE card SET upright_meaning='勤奋的信使、专注学习、务实、新机会', reversed_meaning='懒散、拖延、不切实际' WHERE id=74;
UPDATE card SET upright_meaning='踏实、勤勉、可靠、稳步前行', reversed_meaning='停滞、保守、缺乏灵活' WHERE id=75;
UPDATE card SET upright_meaning='务实、滋养、丰盛、脚踏实地', reversed_meaning='过度物质、忽视内心、依赖' WHERE id=76;
UPDATE card SET upright_meaning='成就、富足、稳健、事业有成', reversed_meaning='固执、贪婪、只重物质' WHERE id=77;
