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
#
# DROP TABLE IF EXISTS `tb_lottery_user_attended`;
# DROP TABLE IF EXISTS `tb_lottery_item_status`;
# DROP TABLE IF EXISTS `tb_lottery_status`;
# DROP TABLE IF EXISTS `tb_lottery_item`;
# DROP TABLE IF EXISTS `tb_lottery`;
#
# CREATE TABLE IF NOT EXISTS `tb_lottery`
# (
#     id         BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'ID',
#     expiration DATETIME               NOT NULL COMMENT '过期时间'
# )
#     COMMENT '神秘商店奖池';
#
# CREATE TABLE IF NOT EXISTS `tb_lottery_status`
# (
#     id         BIGINT(20) NOT NULL PRIMARY KEY COMMENT 'id',
#     lottery_id BIGINT(20) NOT NULL COMMENT '奖池ID',
#     expiration DATETIME   NOT NULL COMMENT '过期时间',
#     foreign key (lottery_id) references tb_lottery (id)
#         on update cascade
#         on delete cascade
# ) COMMENT '奖池的实时状态';
#
# CREATE TABLE IF NOT EXISTS `tb_lottery_item`
# (
#     id         BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
#     lottery_id BIGINT(20) NOT NULL COMMENT '神秘商店当前数据',
#     goods_id   BIGINT(20) NOT NULL COMMENT '物品id',
#     goods_size INT        NOT NULL COMMENT '物品的数目',
#     size       INT        NOT NULL COMMENT '总次数',
#     foreign key (lottery_id) references tb_lottery (id)
#         on update cascade
#         on delete cascade,
#     foreign key (goods_id) references tb_goods (id)
#         on update cascade
#         on delete cascade
# ) COMMENT '奖池项';
#
#
# CREATE TABLE IF NOT EXISTS `tb_lottery_item_status`
# (
#     id                BIGINT(20) NOT NULL PRIMARY KEY COMMENT 'id',
#     lottery_status_id BIGINT(20) NOT NULL COMMENT '奖池的实时状态 ID',
#     lottery_item_id   BIGINT(20) NOT NULL COMMENT '奖池项的代号',
#     used_size         INT        NOT NULL COMMENT '已经参与的次数',
#     foreign key (lottery_item_id) references tb_lottery_item (id)
#         on update cascade
#         on delete cascade,
#     foreign key (lottery_status_id) references tb_lottery_status (id)
#         on update cascade
#         on delete cascade
# ) COMMENT '当前奖池项状态';
#
#
#
# CREATE TABLE IF NOT EXISTS `tb_lottery_user_attended`
# (
#     id                     BIGINT(20) NOT NULL PRIMARY KEY COMMENT 'id',
#     user_id                BIGINT(20) NOT NULL COMMENT '用户ID',
#     lottery_item_status_id BIGINT(20) NOT NULL COMMENT '参与的奖池',
#     size                   INT        NOT NULL DEFAULT 0 COMMENT '已经参与的数目',
#     foreign key (lottery_item_status_id) references tb_lottery_item_status (id)
#         on update cascade
#         on delete cascade
#
# ) COMMENT '用户参与的奖池数目';
#
#
# INSERT INTO `tb_lottery` (`id`, `expiration`)
# VALUES (1, '2020-08-20 00:00:00');
# INSERT INTO `tb_lottery` (`id`, `expiration`)
# VALUES (2, '2039-07-15 20:55:26');
#
#
# INSERT INTO `tb_lottery_item` (`id`, `lottery_id`, `goods_id`, `goods_size`, `size`)
# VALUES (1, 1, 1, 100, 300);
# INSERT INTO `tb_lottery_item` (`id`, `lottery_id`, `goods_id`, `goods_size`, `size`)
# VALUES (2, 1, 722958015550165460, 1231, 1231);
# INSERT INTO `tb_lottery_item` (`id`, `lottery_id`, `goods_id`, `goods_size`, `size`)
# VALUES (3, 1, 3, 12, 123);
# INSERT INTO `tb_lottery_item` (`id`, `lottery_id`, `goods_id`, `goods_size`, `size`)
# VALUES (4, 2, 4, 1231, 1231);
# INSERT INTO `tb_lottery_item` (`id`, `lottery_id`, `goods_id`, `goods_size`, `size`)
# VALUES (5, 2, 722958015550165460, 123, 12);
#
DROP TABLE IF EXISTS tb_lottery_user;
DROP TABLE IF EXISTS tb_lottery;

CREATE TABLE IF NOT EXISTS `tb_lottery`
(
    id         BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    goods_id   BIGINT(20) NOT NULL,
    goods_size INT        NOT NULL,
    size       INT        NOT NULL,
    foreign key (goods_id) references tb_goods (id)
        on update cascade
        on delete cascade

);

CREATE TABLE IF NOT EXISTS `tb_lottery_user`
(
    id            BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    tb_lottery_id BIGINT(20) NOT NULL,
    user_id       BIGINT(20) NOT NULL,
    foreign key (tb_lottery_id) references tb_lottery (id)
        on update cascade
        on delete cascade,
    foreign key (user_id) references tb_user (user_id)
        on update cascade
        on delete cascade
);
