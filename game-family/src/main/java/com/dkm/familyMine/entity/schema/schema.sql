DROP TABLE IF EXISTS `tb_family_mine`;
CREATE TABLE IF NOT EXISTS `tb_family_mine`
(
    `familyId`      BIGINT(20) NOT NULL PRIMARY KEY COMMENT '家族矿区ID ，对应家族ID',
    `battleFieldId` BIGINT(20) COMMENT '战场 ID'
);

DROP TABLE IF EXISTS `tb_family_mine_battle_field_user`;
CREATE TABLE IF NOT EXISTS `tb_family_mine_battle_field_user`
(
    `user_id`         BIGINT(20) PRIMARY KEY NOT NULL COMMENT '用户ID',
    `battle_field_id` BIGINT(20)             NOT NULL COMMENT '战场矿区ID'
);

DROP TABLE IF EXISTS `tb_family_mine_battle_field`;
CREATE TABLE IF NOT EXISTS `tb_family_mine_battle_field`
(
    `battle_field_id`  BIGINT(20) NOT NULL COMMENT '战场矿区ID',
    `familyIdByFirst`  BIGINT(20) NOT NULL default 0 COMMENT '一号家族',
    `familyIdBySecond` BIGINT(20) NOT NULL default 0 COMMENT '二号家族',
    `familyIdByThird`  BIGINT(20) NOT NULL default 0 COMMENT '三号家族',
    `familyIdByForth`  BIGINT(20) NOT NULL default 0 COMMENT '四号家族'
);



DROP TABLE IF EXISTS `tb_family_mine_battle_field_item`;
CREATE TABLE IF NOT EXISTS `tb_family_mine_battle_field_item`
(
    `battle_field_id` BIGINT(20) NOT NULL COMMENT '战场矿区ID',
    `goldItemId`      BIGINT(20) NOT NULL COMMENT '金矿ID',
    `userId`          BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID，如果为0则为未占领',
    `level`           INT        NOT NULL DEFAULT 0 COMMENT '等级',
    `familyId`        BIGINT(20) NOT NULL DEFAULT 0 COMMENT '家族 ID ，默认0代表未占领',
    `index`           INT        NOT NULL COMMENT '位置索引',
    `location_x`      INT        NOT NULL COMMENT 'X 轴坐标',
    `location_y`      INT        NOT NULL COMMENT 'Y 轴坐标',
    primary key (battle_field_id, goldItemId)
);


