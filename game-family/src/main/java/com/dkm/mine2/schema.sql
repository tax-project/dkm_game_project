DROP TABLE IF EXISTS `tb_mine_battle_item`;
DROP TABLE IF EXISTS `tb_mine_battle`;
DROP TABLE IF EXISTS `tb_mine_battle_level`;

CREATE TABLE IF NOT EXISTS `tb_mine_battle`
(
    id               BIGINT(20) PRIMARY KEY NOT NULL,
    first_family_id  BIGINT(20) COMMENT '第一个家族位置',
    second_family_id BIGINT(20) COMMENT '第二个家族位置',
    third_family_id  BIGINT(20) COMMENT '第三个家族位置',
    fourth_family_id BIGINT(20) COMMENT '第四个家族位置'
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
    id               BIGINT(20) NOT NULL PRIMARY KEY COMMENT '矿场ID',
    battle_id        BIGINT(20) NOT NULL COMMENT '矿区ID',
    belong_item      INT        NOT NULL DEFAULT 0 COMMENT '归属，公开矿区为0 ，一号家族为 1，二号家族为 2 .... ',
    user_id          BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否被占领，如果为0则为未占领，如果不是那么则为被占领的用户ID',
    mining_stop_date DATETIME   NOT NULL DEFAULT '2000-1-1 00:00:00' COMMENT '挖矿的结束时间',
    item_level       INT        NOT NULL COMMENT '矿场等级',
    foreign key (battle_id) references tb_mine_battle (id)
        on update cascade
        on delete cascade,
    foreign key (item_level) references tb_mine_battle_level (level)
        on update cascade
        on delete cascade
)
    COMMENT '矿场表';