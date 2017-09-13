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

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO t_user VALUES ('1', '111111', '赵大宝', '13285250574', '1045221654@qq.com', '96e79218965eb72c92a549dd5a330112', '2016-07-15 23:38:56', '2016-07-15 23:39:09', '0');
INSERT INTO t_user VALUES ('2', '222222', '张三丰', '15985250574', '1198224554@qq.com', '96e79218965eb72c92a549dd5a330112', '2016-07-15 23:39:01', '2016-07-15 23:39:13', '0');
INSERT INTO t_user VALUES ('3', '333333', '王尼玛', '13685250574', '1256221654@qq.com', '96e79218965eb72c92a549dd5a330112', '2016-07-15 23:39:05', '2016-07-15 23:39:16', '0');