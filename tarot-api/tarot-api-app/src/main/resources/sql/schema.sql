CREATE TABLE IF NOT EXISTS spread (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    code         VARCHAR(32)  NOT NULL COMMENT '牌型编码，如 single/three-card/celtic-cross',
    name         VARCHAR(64)  NOT NULL COMMENT '牌型名称',
    description  VARCHAR(255) NOT NULL COMMENT '牌型说明',
    card_count   INT          NOT NULL COMMENT '所需卡牌数量',
    positions    JSON         NOT NULL COMMENT '位置定义数组[{index,label,description}]',
    UNIQUE KEY uk_spread_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='塔罗牌型';

CREATE TABLE IF NOT EXISTS card (
    id       INT PRIMARY KEY COMMENT '卡牌ID(0-77)',
    name     VARCHAR(64) NOT NULL COMMENT '中文名',
    name_en  VARCHAR(64) NOT NULL COMMENT '英文名',
    arcana   VARCHAR(16) NOT NULL COMMENT 'major/minor',
    suit     VARCHAR(16) NULL COMMENT '花色(小阿卡纳)',
    `rank`   VARCHAR(16) NULL COMMENT '点数(小阿卡纳)',
    image    VARCHAR(128) NOT NULL COMMENT '图片路径',
    upright_meaning  VARCHAR(512) NULL COMMENT '正位含义',
    reversed_meaning VARCHAR(512) NULL COMMENT '逆位含义'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='塔罗卡牌';

CREATE TABLE IF NOT EXISTS tarot_history (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT       NOT NULL COMMENT '用户ID',
    session_id    VARCHAR(64)  NOT NULL COMMENT '占卜会话ID',
    question      VARCHAR(512) NOT NULL COMMENT '用户问题',
    spread_code   VARCHAR(32)  NOT NULL COMMENT '牌型编码',
    drawn_cards   JSON         NOT NULL COMMENT '抽到的牌[{id,name,reversed,positionIndex}]',
    interpretation MEDIUMTEXT  NULL COMMENT 'LLM解读结果',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_user_created (user_id, created_at),
    UNIQUE KEY uk_session (session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='占卜历史';

CREATE TABLE IF NOT EXISTS tarot_user (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(254) NOT NULL COMMENT '规范化后的邮箱地址',
    password_hash   VARCHAR(100) NOT NULL COMMENT 'BCrypt 密码哈希',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '1-正常，0-禁用',
    last_login_at   DATETIME     NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tarot_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='塔罗用户';

CREATE TABLE IF NOT EXISTS user_session (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT       NOT NULL,
    token_id           VARCHAR(64)  NOT NULL COMMENT '刷新令牌 JWT ID',
    refresh_token_hash CHAR(64)     NOT NULL COMMENT '刷新令牌 SHA-256 哈希',
    expires_at         DATETIME     NOT NULL,
    revoked_at         DATETIME     NULL,
    created_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_used_at       DATETIME     NULL,
    UNIQUE KEY uk_user_session_token_id (token_id),
    KEY idx_user_session_user (user_id),
    KEY idx_user_session_expires (expires_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户多设备登录会话';
