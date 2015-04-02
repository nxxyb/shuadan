
/**建立数据库*/
CREATE DATABASE `shuadan` DEFAULT CHARACTER SET utf8;

use `shuadan`;

/**用户表 */
DROP TABLE IF EXISTS `shuadan_user`;
CREATE TABLE `shuadan_user` (
  `id` INT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `chezhu_name` varchar(50) comment '车主姓名',
  `chezhu_mobile` varchar(50) comment '车主手机号',
  `chezhu_password` varchar(50) comment '车主密码',
  `chezhu_city` varchar(50) comment '车主所在城市',
  `chezhu_place` varchar(40) comment '车主所在道路或地名',
  `chengke_name` varchar(50) comment '乘客姓名',
  `chengke_mobile` varchar(50) comment '乘客手机号',
  `chengke_password` varchar(50) comment '乘客密码',
  `chengke_city` varchar(50) comment '乘客所在城市',
  `chengke_place` varchar(40) comment '乘客所在道路或地名',
  `chengke_fd` varchar(40) comment '乘客是否发单 0-未发 1-已发  2-已发且有车主正在抢单 3-车主抢单完成 4-乘客付款完成 5-乘客确认搭乘 6-乘客评价完成',
  `chengke_fd_time` varchar(40) comment '乘客发单时间',
  `chezhu_qd_time` varchar(40) comment '车主抢单时间'
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/**订单日志表 */
DROP TABLE IF EXISTS `shuadan_log`;
CREATE TABLE `shuadan_log` (
  `id` INT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT(8) comment '用户id',
  `chezhu_isqd` varchar(40) comment '抢单状态  0-乘客已发单  1-车主正在抢单  3-抢单完成  4-抢单失败',
  `chezhu_qd_yy` varchar(40) comment '抢单失败原因',
  `qd_time` varchar(50) comment '抢单完成时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** 插入用户*/
insert into shuadan_user (chezhu_name, chezhu_mobile, chezhu_password, chezhu_city, chezhu_place, chengke_name, chengke_mobile, chengke_password, chengke_city, chengke_place, chengke_fd,chengke_fd_time,chezhu_qd_time)
                  values ('徐雁斌'   , '18622535271', '5034718'       ,'天津',       '侯台',       '郑向辉',     '13821008956',  '604561',         '天津市',      '雅士道',      '0',        '',            ''            );


