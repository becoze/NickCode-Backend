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


drop table problem;
-- Problems table
create table if not exists problem
(
    id          bigint auto_increment comment 'ID' primary key,
    title       varchar(512)                       null comment 'Problem title',
    content     text                               null comment 'Question content',
    tags        varchar(1024)                      null comment 'Problem tags (JSON array)',
    answer      text                               null comment 'Problem answer',
    submitNum   int      default 0                 not null comment 'Total submitted',
    acceptedNum int      default 0                 not null comment 'Total accepted',
    thumbNum    int      default 0                 not null comment 'Number of likes / thumb up',
    favourNum   int      default 0                 not null comment 'Number of favour / collection',
    judgeCase   text                               null comment 'Judge Case (JSON array)',
    judgeConfig text                               null comment 'Judge Configuration (JSON Object)',
    userId      bigint                             not null comment 'Id of the creator user',
    createTime  datetime default CURRENT_TIMESTAMP not null comment 'Creation Time',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'Update Time',
    isDelete    tinyint  default 0                 not null comment 'Deleted Status'
) comment 'Problem' collate = utf8mb4_unicode_ci;

-- Submit table
create table if not exists problem_submit
(
    id         bigint auto_increment comment 'ID' primary key,
    language   varchar(128)                       not null comment 'Used programming language',
    code       text                               not null comment 'Submit user code',
    judgeInfo  text                               null comment 'Judge result (JSON Object)',
    status     int      default 0                 not null comment 'Judge status (0-pending, 1-processing, 2-success, 3-fail)',
    problemId  bigint                             not null comment 'Problem id',
    userId     bigint                             not null comment 'Id of the creator user',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'Creation time',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'Update Time',
    isDelete   tinyint  default 0                 not null comment 'Deleted Status',
    index idx_problemId (problemId),
    index idx_userId (userId)
) comment 'Problem_Submit' collate = utf8mb4_unicode_ci;