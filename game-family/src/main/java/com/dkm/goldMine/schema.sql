DROP TABLE IF EXISTS `tb_family_mine_item`;
DROP TABLE IF EXISTS tb_family_mine_user;
DROP TABLE IF EXISTS tb_family_mine;

CREATE TABLE IF NOT EXISTS tb_family_mine
(
    id       BIGINT(20) PRIMARY KEY NOT NULL,
    familyId BIGINT(20)             NOT NULL COMMENT '家族id'
)
    COMMENT '家族金矿表'
;

CREATE TABLE IF NOT EXISTS tb_family_mine_user
(
    userId        BIGINT(20) PRIMARY KEY NOT NULL,
    fightSize     INT                    NOT NULL DEFAULT 3,
    lastFightDate DATETIME               NOT NULL
)
    COMMENT '家族用户 id 表';


CREATE TABLE IF NOT EXISTS `tb_family_mine_item`
(
    goldItemId     BIGINT(20) NOT NULL COMMENT '金矿ID',
    familyId       BIGINT(20) NOT NULL COMMENT '家族 ID',
    battle_id      BIGINT(20) NOT NULL COMMENT '战场矿区ID',
    userId         BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID，如果为0则为未占领',
    fightEndDate   DATETIME   NOT NULL DEFAULT '2000-1-1 00:00:00',
    fightStartDate DATETIME   NOT NULL DEFAULT '2000-1-1 00:00:00',
    level          INT        NOT NULL COMMENT '等级',
    `index`        INT        NOT NULL COMMENT '位置索引',
    location_x     INT        NOT NULL COMMENT 'X 轴坐标',
    location_y     INT        NOT NULL COMMENT 'Y 轴坐标',
    primary key (goldItemId, familyId),
    foreign key (battle_id) references tb_family_mine (id)
        on update cascade
        on delete cascade
)
    COMMENT '矿区金矿表'
;
