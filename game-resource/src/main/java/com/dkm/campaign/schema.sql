DROP TABLE IF EXISTS tb_game_options;
CREATE TABLE IF NOT EXISTS `tb_game_options`
(
    id             BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '默认的配置ID，自增',
    `option_key`   VARCHAR(255)           NOT NULL,
    `option_value` VARCHAR(255)           NULL DEFAULT NULL COMMENT '默认的配置选项'
)
    COMMENT '一些配置的表'
;
# DROP TABLE IF EXISTS `tb_commodity`;
# DROP TABLE IF EXISTS `tb_commodity_type`;
# CREATE TABLE IF NOT EXISTS `tb_commodity_type`
# (
#     id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '默认ID',
#     name VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '类型名称'
# ) COMMENT '商品的类型';
#
# INSERT INTO `tb_commodity_type` (id, name) VALUE (0, '默认商品');
#
#
# CREATE TABLE IF NOT EXISTS `tb_commodity`
# (
#     id          BIGINT PRIMARY KEY NOT NULL COMMENT '默认ID',
#     `name`      VARCHAR(255)       NOT NULL COMMENT '商品名称',
#     `type`      BIGINT             NOT NULL DEFAULT 0 COMMENT '商品类型',
#     `details`   VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '商品详情',
#     `image_url` VARCHAR(255)       NOT NULL DEFAULT '' COMMENT '商品图片地址',
#     `price`     BIGINT(10)         NOT NULL DEFAULT 0 COMMENT '商品价格',
#     foreign key (type) references tb_commodity_type (id)
#         on update cascade
#         on delete cascade
# )
#     COMMENT '商品表';

DROP TABLE IF EXISTS `tb_lottery_item`;
DROP TABLE IF EXISTS `tb_lottery`;

CREATE TABLE IF NOT EXISTS `tb_lottery`
(
    id         BIGINT(20) PRIMARY KEY NOT NULL COMMENT 'ID',
    expiration DATETIME               NOT NULL COMMENT '过期时间'
)
    COMMENT '神秘商店';

CREATE TABLE IF NOT EXISTS `tb_lottery_item`
(
    id                BIGINT(20) NOT NULL PRIMARY KEY COMMENT 'id',
    lottery_id        BIGINT(20) NOT NULL COMMENT '神秘商店当前数据',
    goods_id          BIGINT(20) NOT NULL COMMENT '物品id',
    goods_size        INT        NOT NULL COMMENT '物品的数目',
    already_used_size INT        NOT NULL COMMENT '已经参与的次数',
    all_size          INT        NOT NULL COMMENT '总参与的次数',
    foreign key (lottery_id) references tb_goods (id)
        on update cascade
        on delete cascade,
    foreign key (goods_id) references tb_lottery (id)
        on update cascade
        on delete cascade
) COMMENT '抽取的物品';

CREATE TABLE IF NOT EXISTS `tb_lottery_user_attended`
(
    id BIGINT(20) NOT NULL PRIMARY KEY COMMENT 'id'

) COMMENT '用户参与的奖池';