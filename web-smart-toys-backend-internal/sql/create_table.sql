# 数据库初始化


-- 创建库
create database if not exists yeasier_db;

-- 切换库
use yeasier_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    userEmail    varchar(256)                           null comment '用户邮箱',
    userBalance  decimal(10, 2) default 0.00              not null comment '用户余额',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    defaultAgentId bigint         default 0                 not null comment '默认agentId',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 沙盒表
create table if not exists sandbox
(
    id         bigint auto_increment comment 'id' primary key,
    sandboxType varchar(256)                           not null comment '沙盒类型',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '沙盒' collate = utf8mb4_unicode_ci;

-- 智能体表
create table if not exists agent
(
    id         bigint auto_increment comment 'id' primary key,
    agentName varchar(256)                           not null comment '智能体名称',
    agentProfile varchar(1024)                        not null comment '智能体描述',
    agentWakeword varchar(256)                       not null comment '智能体唤醒词',
    agentMemory text                        not null comment '智能体记忆',
    agentPosition text                         not null comment '智能体位置信息',
    agentAction text                         not null comment '智能体当前正在做的行为',
    userId bigint                             not null comment '创建用户 id',
    sandboxId bigint                             not null comment '所属沙盒 id',
    onlineStatus tinyint default 0                 not null comment '在线状态：0-离线 1-在线',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '智能体' collate = utf8mb4_unicode_ci;