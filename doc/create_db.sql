
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
  `chezhu_jplace` varchar(40) comment '车主家所在道路或地名',
  `chezhu_dplace` varchar(40) comment '车主单位所在道路或地名',
  `chezhu_deviceid` varchar(40) comment '车主设备序列号',
  `chezhu_zfb_num` varchar(40) comment '车主支付宝帐号',
  `chezhu_zfb_password` varchar(40) comment '车主支付宝密码',
  `chengke_name` varchar(50) comment '乘客姓名',
  `chengke_mobile` varchar(50) comment '乘客手机号',
  `chengke_password` varchar(50) comment '乘客密码',
  `chengke_city` varchar(50) comment '乘客所在城市',
  `chengke_jplace` varchar(40) comment '乘客家所在道路或地名',
  `chengke_dplace` varchar(40) comment '乘客单位所在道路或地名',
  `chengke_deviceid` varchar(40) comment '乘客设备序列号',
  `chengke_zfb_num` varchar(40) comment '乘客支付宝帐号',
  `chengke_zfb_password` varchar(40) comment '乘客支付宝密码',
  `fd_status` varchar(40) comment '乘客是否发单 0-未发 1-已发  2-已发且有车主正在抢单 3-车主抢单完成  4-乘客确认搭乘 5-乘客评价完成',
  `fk_status` varchar(40) comment '乘客是否付款 0-未付 1-已付',
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
insert into shuadan_user (chezhu_name, chezhu_mobile, chezhu_password, chezhu_city, chezhu_jplace, chezhu_dplace, chezhu_deviceid,chezhu_zfb_num,chezhu_zfb_password, chengke_name, chengke_mobile, chengke_password, chengke_city, chengke_jplace, chengke_dplace, chengke_deviceid,chengke_zfb_num,chengke_zfb_password,fd_status,fk_status,chengke_fd_time,chezhu_qd_time)
                  values ('徐雁斌'   , '18622535271', '5034718'       ,'天津',       '侯台',        '海泰发展',     '',             '',            '',                 '郑向辉',     '13821008956',  '604561',          '天津',       '侯台',         '天津南站',      '',              '',             '',                  '0',      '0',      '',             ''            );


insert into shuadan_user (chezhu_name, chezhu_mobile, chezhu_password, chezhu_city, chezhu_jplace, chezhu_dplace, chezhu_deviceid,chezhu_zfb_num,chezhu_zfb_password, chengke_name, chengke_mobile, chengke_password, chengke_city, chengke_jplace, chengke_dplace, chengke_deviceid,chengke_zfb_num,chengke_zfb_password,fd_status,fk_status,chengke_fd_time,chezhu_qd_time)
                  values ('徐雁斌'   , '18622535271', '5034718'       ,'天津',       '侯台',        '海泰发展',     '',             '',            '',                 '周勤红',     '13412846320',  '201514',          '天津',       '侯台',         '天津南站',      '',              '',             '',                  '0',      '0',      '',             ''            );
