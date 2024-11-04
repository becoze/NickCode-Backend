# Database init

-- Create database
create database if not exists nickcode;

-- Switch and use database
use nickcode;

-- User table
create table if not exists user
(
    id           bigint auto_increment comment 'ID' primary key,
    userAccount  varchar(256)                           not null comment 'Account',
    userPassword varchar(512)                           not null comment 'Password',
    userName     varchar(256)                           null comment 'User Nickname',
    userAvatar   varchar(1024)                          null comment 'User Avatar',
    userProfile  varchar(512)                           null comment 'User Profile',
    userRole     varchar(256) default 'user'            not null comment 'User Role: user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'Creation Time',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'Update Time',
    isDelete     tinyint      default 0                 not null comment 'Deleted Status'
) comment 'User' collate = utf8mb4_unicode_ci;
