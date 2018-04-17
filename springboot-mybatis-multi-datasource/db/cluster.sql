CREATE DATABASE springbootdb_cluster;

DROP TABLE IF EXISTS `user`;
CREATE TABLE user
(
id INT(10) unsigned PRIMARY KEY NOT NULL COMMENT '用户编号' AUTO_INCREMENT,
user_name VARCHAR(25) COMMENT '用户名称',
description VARCHAR(25) COMMENT '描述'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT user VALUES (1 ,'泥瓦匠','他有一个小网站 bysocket.com');
