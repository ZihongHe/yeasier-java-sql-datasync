# 数据库初始化


-- 创建库
create database if not exists yeasier_db;

-- 切换库
use yeasier_db;

create table agent
(
    agentId       bigint auto_increment comment 'id'
        primary key,
    agentName     varchar(256)                       not null comment '智能体名称',
    agentProfile  varchar(1024)                      not null comment '智能体描述',
    agentWakeword varchar(256)                       not null comment '智能体唤醒词',
    agentMemory   text                               null comment '智能体记忆',
    agentPosition text                               null comment '智能体位置信息',
    agentAction   text                               null comment '智能体当前正在做的行为',
    userId        bigint                              null comment '创建用户 id',
    sandboxId     bigint                             null comment '所属沙盒 id',
    onlineStatus  tinyint  default 0                 not null comment '在线状态：0-离线 1-在线',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
)
    comment '智能体' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on agent (agentId);

create table user_agent
(
    userAgentId       bigint auto_increment comment 'id'
        primary key,
    userAgentName     varchar(256)                       not null comment '智能体名称',
    userAgentProfile  varchar(1024)                      not null comment '智能体描述',
    userAgentWakeword varchar(256)                       not null comment '智能体唤醒词',
    userAgentMemory   text                               null comment '智能体记忆',
    userAgentPosition text                               null comment '智能体位置信息',
    userAgentAction   text                               null comment '智能体当前正在做的行为',
    userId        bigint                             not null comment '所属用户 id',
    agentId       bigint                             not null comment '智能体 id',
    sandboxId     bigint                             null comment '所属沙盒 id',
    onlineStatus  tinyint  default 0                 not null comment '在线状态：0-离线 1-在线',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
)
    comment '用户智能体' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user_agent (userAgentId);

create table sandbox
(
    sandboxId   bigint auto_increment comment 'id'
        primary key,
    sandboxName varchar(256)                       not null comment '沙盒名称',
    sandboxType varchar(256)                       not null comment '沙盒类型',
    userId        bigint                            null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '沙盒' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on sandbox (sandboxId);

create table user_sandbox
(
    userSandboxId   bigint auto_increment comment 'id'
        primary key,
    sandboxName varchar(256)                       not null comment '沙盒名称',
    sandboxType varchar(256)                       not null comment '沙盒类型',
    userId        bigint                             not null comment '所属用户 id',
    sandboxId     bigint                             not null comment '沙盒 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '沙盒' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user_sandbox (userSandboxId);

create table user
(
    userId         bigint auto_increment comment 'id'
        primary key,
    userAccount    varchar(256)                             not null comment '账号',
    userPassword   varchar(512)                             not null comment '密码',
    userName       varchar(256)                             null comment '用户昵称',
    userRole       varchar(256)   default 'user'            not null comment '用户角色：user/admin/ban',
    userEmail      varchar(256)                             null comment '用户邮箱',
    userBalance    decimal(10, 2) default 0.00              not null comment '用户余额',
    createTime     datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint        default 0                 not null comment '是否删除'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (userId);

create table achievement
(
    achievementId       bigint auto_increment comment 'id'
        primary key,
    achievementName     varchar(256)                       not null comment '成就名称',
    achievementProfile  varchar(256)                      not null comment '成就描述',
    firstAchievedUserId  bigint                             null comment '首次获得该成就的用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
)
    comment '成就' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on achievement (achievementId);


create table user_achievement
(
    userAchievementId       bigint auto_increment comment 'id'
        primary key,
    achievementId bigint                             not null comment '成就 id',
    userId        bigint                             not null comment '用户 id',
    userRank          bigint                             not null comment '获得该成就位次',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
)
    comment '用户成就' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user_achievement (userAchievementId);


