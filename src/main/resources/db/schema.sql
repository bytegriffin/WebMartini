/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/25 20:46:05                          */
/*==============================================================*/


drop table if exists `group_role`;

drop table if exists `group_user`;

drop table if exists `system_group`;

drop table if exists `menu`;

drop table if exists `menu_role`;

drop table if exists `message`;

drop table if exists `roles`;

drop table if exists `user_log`;

drop table if exists `user_message`;

drop table if exists `user_role`;

drop table if exists `users`;

/*==============================================================*/
/* Table: group_role                                            */
/*==============================================================*/
create table `group_role`
(
   id                   varchar(32) not null,
   role_id              int,
   group_id             int,
   primary key (id)
);

/*==============================================================*/
/* Table: group_user                                            */
/*==============================================================*/
create table `group_user`
(
   id                   varchar(32) not null,
   group_id             int,
   user_id              int,
   primary key (id)
);

/*==============================================================*/
/* Table: system_group                                                */
/*==============================================================*/
create table `system_group`
(
   id                   int not null auto_increment,
   parent_id            int,
   name                 varchar(20),
   description          varchar(100),
   code                 varchar(400),
   primary key (id)
);

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table `menu`
(
   id                   int not null auto_increment,
   parent_id            int,
   name                 varchar(20),
   url                  varchar(1024),
   priority             int,
   status               varchar(20) comment '0: 关   1: 开',
   levels               int,
   icon                 varchar(1024),
   description          varchar(100),
   code                 varchar(400),
   primary key (id)
);

/*==============================================================*/
/* Table: menu_role                                             */
/*==============================================================*/
create table `menu_role`
(
   id                   varchar(32) not null,
   menu_id              int,
   role_id              int,
   primary key (id)
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table `message`
(
   id                   int not null auto_increment,
   title                varchar(100),
   content              text,
   send_id              int,
   rec_id               varchar(32),
   send_time            datetime,
   save_time            datetime,
   status               varchar(10) comment '0：草稿箱
            1：已发送',
   role_ids             text,
   group_ids            text,
   attachment1          varchar(1024),
   attachment2          varchar(1024),
   attachment3          varchar(1024),
   primary key (id)
);

/*==============================================================*/
/* Table: roles                                                 */
/*==============================================================*/
create table `roles`
(
   id                   int not null auto_increment,
   name                 varchar(20),
   parent_id            int,
   description          varchar(100),
   code                 varchar(400),
   primary key (id)
);

/*==============================================================*/
/* Table: user_log                                              */
/*==============================================================*/
create table `user_log`
(
   id                   varchar(32) not null,
   user_id              int,
   user_name            varchar(20),
   oper_type            varchar(10),
   oper_content         varchar(1024),
   oper_time            datetime,
   oper_ip              varchar(30),
   browser              varchar(30),
   os                   varchar(30),
   exception            text,
   url                  varchar(1024),
   method               varchar(10),
   controller           varchar(1024),
   http_status          varchar(10),
   primary key (id)
);

/*==============================================================*/
/* Table: user_message                                          */
/*==============================================================*/
create table `user_message`
(
   id                   int not null auto_increment,
   send_id              int,
   rec_id               int,
   send_time            datetime,
   status               varchar(10) comment '0：未读
            1：已读',
   msg_id               int,
   primary key (id)
);

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table `user_role`
(
   id                   varchar(32) not null,
   role_id              int,
   user_id              int,
   primary key (id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table `users`
(
   id                   int not null auto_increment,
   name                 varchar(20),
   login_name           varchar(20),
   sex                  varchar(10),
   birthday             varchar(20),
   avatar               varchar(1024),
   phone                varchar(20),
   email                varchar(50),
   id_number            varchar(20),
   password             varchar(32),
   status               varchar(20) comment '1.正常  2.锁定 ',
   regist_time          datetime,
   login_time           datetime,
   login_count          int,
   login_ip             varchar(30),
   primary key (id)
);

