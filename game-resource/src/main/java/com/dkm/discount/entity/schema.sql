DROP TABLE IF EXISTS `tb_lottery_item`;
DROP TABLE IF EXISTS `tb_lottery`;
CREATE TABLE IF NOT EXISTS `tb_lottery`
(
    id         BIGINT(20) PRIMARY KEY NOT NULL COMMENT '奖池代号',
    cycle_size BIGINT(20)             NOT NULL DEFAULT -1 COMMENT '循环次数,其中 -1 代表一直循环',
    duration   BIGINT(20)             NOT NULL DEFAULT 3600 COMMENT '奖池持续时间，单位为秒',
    start_date DATETIME               NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '开始的时间',
    stop_date  DATETIME               NOT NULL DEFAULT '2025-01-01 00:00:00' COMMENT '结束的时间'
)
    COMMENT = '神秘商店抽奖索引'
;
CREATE TABLE IF NOT EXISTS `tb_lottery_item`
(
    id            BIGINT(20) PRIMARY KEY NOT NULL COMMENT '奖池品种ID',
    tb_lottery_id BIGINT(20)             NOT NULL COMMENT '奖池代号',
    prize_id      BIGINT(20)             NOT NULL COMMENT '奖品ID',
    prize_size    BIGINT(20)             NOT NULL COMMENT '奖品数目',
    foreign key (tb_lottery_id) references tb_lottery (id)
        on update cascade
        on delete cascade
)
    COMMENT = '神秘商店物品'
;

DROP TABLE IF EXISTS `tb_lottery_user_buy`;

CREATE TABLE IF NOT EXISTS `tb_lottery_user_buy`
(
    id       BIGINT(20) PRIMARY KEY NOT NULL COMMENT '奖池ID',
    user_id  BIGINT(20)             NOT NULL COMMENT '用户ID',
    buy_size BIGINT(20)             NOT NULL COMMENT '购买钻石的数目'
)
    COMMENT = '用户购买的奖卷表';


DROP TABLE IF EXISTS `tb_lottery_user_history`;
CREATE TABLE IF NOT EXISTS `tb_lottery_user_history`
(
    id         BIGINT(20) PRIMARY KEY NOT NULL COMMENT '历史记录ID',
    user_id    BIGINT(20)             NOT NULL COMMENT '用户ID',
    prize_id   BIGINT(20)             NOT NULL COMMENT '奖品ID',
    prize_size BIGINT(20)             NOT NULL COMMENT '奖品数目',
    prize_date BIGINT(20)             NOT NULL COMMENT '获奖日期'
)
    COMMENT = '中奖历史记录';

