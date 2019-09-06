DROP DATABASE IF EXISTS users;

CREATE TABLE users (
  id varchar(64) NOT NULL,
  username varchar(20) NOT NULL COMMENT '用户名',
  nickname varchar(20) NOT NULL COMMENT '昵称',
  password varchar(64) NOT NULL COMMENT '密码',
  avatar varchar(255) NOT NULL COMMENT '头像',
  avatar_big varchar(255) NOT NULL COMMENT '头像大图',
  qrcode varchar(255) NOT NULL COMMENT '二维码',
  cid varchar(64) COMMENT '设备ID',
  create_time timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  update_time timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '修改时间',
  primary key (id)
) COMMENT '用户表';

DROP DATABASE IF EXISTS friends_request;

CREATE TABLE friends_request (
  id varchar(64) NOT NULL,
  send_user_id varchar(64) NOT NULL COMMENT '发送者',
  accept_user_id varchar(64) NOT NULL COMMENT '接收者',
  request_date_time timestamp NOT NULL DEFAULT current_timestamp COMMENT '发送时间',
  primary key (id)
) COMMENT '用户请求表';

DROP DATABASE IF EXISTS my_friends;

CREATE TABLE my_friends (
  id varchar(64) NOT NULL,
  my_user_id varchar(64) NOT NULL COMMENT '用户ID',
  my_friend_user_id varchar(64) NOT NULL COMMENT '用户朋友ID',
  primary key (id)
) COMMENT '用户关联表';


DROP DATABASE IF EXISTS chat_msg;

CREATE TABLE chat_msg (
  id varchar(64) NOT NULL,
  send_user_id varchar(64) NOT NULL COMMENT '发送者',
  accept_user_id varchar(64) NOT NULL COMMENT '接收者',
  msg varchar(255) NOT NULL COMMENT '消息内容',
  sign_flag tinyint(3) NOT NULL COMMENT '是否签收',
  create_time timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  primary key (id)
) COMMENT '聊天消息表';