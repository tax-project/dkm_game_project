DROP TABLE IF EXISTS `tb_family_mines_item`;
DROP TABLE IF EXISTS tb_family_mines_user;
DROP TABLE IF EXISTS tb_family_mines;

CREATE TABLE IF NOT EXISTS tb_family_mines
(
    id        BIGINT(20) PRIMARY KEY NOT NULL,
    family_id BIGINT(20)             NOT NULL COMMENT '家族id'
)
    COMMENT '家族金矿表'
;

CREATE TABLE IF NOT EXISTS tb_family_mines_user
(
    user_id         BIGINT(20) PRIMARY KEY NOT NULL,
    fight_size      INT                    NOT NULL DEFAULT 3,
    last_fight_date DATETIME               NOT NULL
)
    COMMENT '家族用户 id 表';


CREATE TABLE IF NOT EXISTS `tb_family_mines_item`
(
    gold_item_id     BIGINT(20) NOT NULL COMMENT '金矿ID',
    family_id        BIGINT(20) NOT NULL COMMENT '家族 ID',
    battle_id        BIGINT(20) NOT NULL COMMENT '战场矿区ID',
    user_id          BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID，如果为0则为未占领',
    local            INT        NOT NULL COMMENT '是否属于私人领地 （0，1）',
    fight_end_date   DATETIME   NOT NULL DEFAULT '2000-1-1 00:00:00',
    fight_start_date DATETIME   NOT NULL DEFAULT '2000-1-1 00:00:00',
    level            INT        NOT NULL COMMENT '等级',
    item_index       INT        NOT NULL COMMENT '位置索引',

    primary key (gold_item_id, family_id),
    foreign key (battle_id) references tb_family_mines (id)
        on update cascade
        on delete cascade
)
    COMMENT '矿区金矿表'
;
