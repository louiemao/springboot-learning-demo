DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS  CITY;


CREATE TABLE user (
  id varchar(36)  NOT NULL COMMENT 'id',
  user_cd varchar(25)   NOT NULL COMMENT '用户编号',
  user_name varchar(25) DEFAULT NULL COMMENT '用户姓名',
  password varchar(36) NOT NULL COMMENT '密码',
  PRIMARY KEY (id)
);
CREATE TABLE CITY
(
  ID integer AUTO_INCREMENT NOT NULL COMMENT '城市编号',
  PROVINCE_ID integer NOT NULL COMMENT '省份编号',
  CITY_NAME varchar(25) DEFAULT 'NULL' COMMENT '城市名称',
  DESCRIPTION varchar(25) DEFAULT 'NULL' COMMENT '描述',
  PRIMARY KEY (id)
);


INSERT INTO user VALUES ('1111111' ,'admin','管理员','111');
INSERT INTO city VALUES (1 ,1,'温岭市','BYSocket 的家在温岭。');