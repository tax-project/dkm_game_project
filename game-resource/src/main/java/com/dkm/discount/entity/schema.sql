DROP TABLE IF EXISTS `tb_lottery`;
CREATE TABLE IF NOT EXISTS `tb_lottery`
(
    id         BIGINT(20) PRIMARY KEY NOT NULL COMMENT '奖池代号',
    cycle_size BIGINT(20)             NOT NULL DEFAULT 0 COMMENT '循环次数',
    duration   BIGINT(20)             NOT NULL DEFAULT 3600 COMMENT '奖池持续时间，单位为秒',
    start_date DATETIME               NOT NULL COMMENT '开始的时间'
)
    COMMENT = '神秘商店抽奖索引'
;

CREATE TABLE IF NOT EXISTS `tb_lottery_item`
(
    id            BIGINT(20) PRIMARY KEY NOT NULL COMMENT '奖池品种ID',
    tb_lottery_id BIGINT(20)             NOT NULL COMMENT '奖池代号',
    prize_id      BIGINT(20)             NOT NULL COMMENT '奖品ID',
    prize_size    BIGINT(20)            NOT NULL COMMENT '奖品代号',
    prize_    BIGINT(20)            NOT NULL COMMENT '奖品代号'


)
    COMMENT = '神秘商店物品'
;
