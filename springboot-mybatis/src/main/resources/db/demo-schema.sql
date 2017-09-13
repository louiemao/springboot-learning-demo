-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id varchar(36) NOT NULL  PRIMARY KEY,
  user_cd varchar(50) default NULL COMMENT '用户编号',
  user_name varchar(255) default NULL COMMENT '用户名',
  user_phone varchar(20) default NULL COMMENT '手机号',
  user_email varchar(255) default NULL COMMENT '邮箱地址',
  user_pwd varchar(32) default NULL COMMENT '用户密码',
  create_time datetime default NULL COMMENT '创建时间',
  modify_time datetime default NULL COMMENT '最后修改时间',
  is_delete tinyint(4) default NULL COMMENT '是否删除，0-未删除；1-已删除'
) ;
