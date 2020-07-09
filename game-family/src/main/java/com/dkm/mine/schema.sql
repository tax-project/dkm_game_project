DROP TABLE IF EXISTS `tb_mine_battle_item`;
DROP TABLE IF EXISTS `tb_mine_battle`;
DROP TABLE IF EXISTS `tb_mine_battle_level`;

CREATE TABLE IF NOT EXISTS `tb_mine_battle`
(
    id               BIGINT(20) NOT NULL PRIMARY KEY,
    first_family_id  BIGINT(20) NOT NULL DEFAULT 0 COMMENT '第一个家族位置',
    second_family_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '第二个家族位置',
    third_family_id  BIGINT(20) NOT NULL DEFAULT 0 COMMENT '第三个家族位置',
    fourth_family_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '第四个家族位置'
)
    COMMENT '家族矿区表，一个矿区4个家族，至少存在一个家族';

CREATE TABLE IF NOT EXISTS `tb_mine_battle_level`
(
    level          INT PRIMARY KEY NOT NULL COMMENT '等级',
    npc_name       VARCHAR(255)    NOT NULL COMMENT '守护者名称',
    gold_yield     INT(10)         NOT NULL COMMENT '金币每小时产出效率',
    integral_yield INT(10)         NOT NULL COMMENT '积分每小时产出效率',
    npc_level      INT(10)         NOT NULL COMMENT '守护者的等级'
)
    COMMENT '矿场等级表';
INSERT INTO tb_mine_battle_level VALUE
    (1, '初级守护者', 8280, 300, 1),
    (2, '初级守护者', 12420, 300, 2),
    (3, '初级守护者', 16560, 360, 3),
    (4, '中级守护者', 20700, 420, 4),
    (5, '中级守护者', 62100, 1440, 5),
    (6, '中级守护者', 66300, 1560, 6),
    (7, '中级守护者', 70300, 1680, 7),
    (8, '中级守护者', 74500, 1800, 8),
    (9, '中级守护者', 91080, 1920, 9),
    (10, '中级守护者', 107700, 2040, 10),
    (11, '高级守护者', 124200, 2160, 11),
    (12, '高级守护者', 140760, 2280, 12),
    (13, '高级守护者', 165600, 2400, 13),
    (14, '高级守护者', 190300, 2520, 14);

CREATE TABLE IF NOT EXISTS `tb_mine_battle_item`
(
    id          BIGINT(20) NOT NULL PRIMARY KEY COMMENT '矿场ID',
    battle_id   BIGINT(20) NOT NULL COMMENT '矿区ID',
    belong_item INT        NOT NULL DEFAULT 0 COMMENT '归属，公开矿区为0 ，一号家族为 1，二号家族为 2 .... ',
    user_id     BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否被占领，如果为0则为未占领，如果不是那么则为被占领的用户ID',
    item_level  INT        NOT NULL COMMENT '矿场等级',
    foreign key (battle_id) references tb_mine_battle (id)
        on update cascade
        on delete cascade,
    foreign key (item_level) references tb_mine_battle_level (level)
        on update cascade
        on delete cascade
)
    COMMENT '矿场表';

DROP TABLE IF EXISTS `tb_mine_family_level_addition`;
CREATE TABLE IF NOT EXISTS `tb_mine_family_level_addition`
(
    family_level INT PRIMARY KEY NOT NULL COMMENT '家族等级',
    level_name   VARCHAR(255)    NOT NULL COMMENT '等级名称',
    addition     DOUBLE          NOT NULL COMMENT '金币加成'
)
    COMMENT '家族等级加成表';

INSERT INTO `tb_mine_family_level_addition` VALUE
    (1, '倔强青铜3', 0.05),
    (2, '倔强青铜2', 0.1),
    (3, '倔强青铜1', 0.15),
    (4, '秩序白银4', 0.2),
    (5, '秩序白银3', 0.25),
    (6, '秩序白银2', 0.3),
    (7, '秩序白银1', 0.35),
    (8, '荣耀黄金5', 0.4),
    (9, '荣耀黄金4', 0.45),
    (10, '荣耀黄金3', 0.5),
    (11, '荣耀黄金2', 0.6),
    (12, '荣耀黄金1', 0.7),
    (13, '尊贵铂金5', 0.8),
    (14, '尊贵铂金4', 0.9),
    (15, '尊贵铂金3', 1.0),
    (16, '尊贵铂金2', 1.1),
    (17, '尊贵铂金1', 1.2),
    (18, '永恒钻石3', 1.3),
    (19, '永恒钻石2', 1.4),
    (20, '永恒钻石1', 1.5)
;

DROP TABLE IF EXISTS `tb_mine_history_goods`;
DROP TABLE IF EXISTS `tb_mine_history`;
DROP TABLE IF EXISTS `tb_mine_user_info`;

CREATE TABLE IF NOT EXISTS `tb_mine_history`
(
    id              BIGINT(20) PRIMARY KEY NOT NULL,
    user_id         BIGINT(20)             NOT NULL COMMENT '用户ID',
    family_id       BIGINT(20)             NOT NULL COMMENT '家族 ID',
    start_date      DATETIME               NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '开始时间',
    stop_date       DATETIME               NOT NULL DEFAULT '2000-01-01 01:00:00' COMMENT '结束时间',
    integral        INT(10)                NOT NULL DEFAULT '300' COMMENT '贡献的积分',
    mine_item_level INT                    NOT NULL COMMENT '占领的矿区等级'
)
    COMMENT '历史记录';


CREATE TABLE IF NOT EXISTS `tb_mine_user_info`
(
    user_id         BIGINT(20) NOT NULL PRIMARY KEY COMMENT '用户ID',
    family_id       BIGINT(20) NOT NULL COMMENT '家族 ID',
    finally_mine_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后挖矿的历史记录'
)
    COMMENT '家族用户的信息'
;
CREATE TABLE IF NOT EXISTS `tb_mine_history_goods`
(
    id              BIGINT(20) PRIMARY KEY NOT NULL,
    mine_history_id BIGINT(20)             NOT NULL,
    goods_id        BIGINT(20)             NOT NULL,
    foreign key (mine_history_id) references tb_mine_history (id)
        on update cascade
        on delete cascade,
    foreign key (goods_id) references tb_goods (id)
        on update cascade
        on delete cascade
) COMMENT '历史记录下获得的额外道具';