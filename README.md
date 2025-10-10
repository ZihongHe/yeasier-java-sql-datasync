# yeasier-java-sql-datasync

# 后端本地部署教程

## 环境准备

jdk17

maven [（2025.1.27）最新版MAVEN的安装和配置教程（超详细）_maven安装及配置教程-CSDN博客](https://blog.csdn.net/m0_73804764/article/details/139898041)

mysql

## 本地启动

### 初始化数据库

```mysql
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
    userId        bigint                             not null comment '创建用户 id',
    sandboxId     bigint                             null comment '所属沙盒 id',
    onlineStatus  tinyint  default 0                 not null comment '在线状态：0-离线 1-在线',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
)
    comment '智能体' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on agent (agentId);

create table sandbox
(
    sandboxId   bigint auto_increment comment 'id'
        primary key,
    sandboxType varchar(256)                       not null comment '沙盒类型',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '沙盒' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on sandbox (sandboxId);

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
    isDelete       tinyint        default 0                 not null comment '是否删除',
    defaultAgentId bigint         default 0                 not null
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (userId);
```

### 运行

使用idea分别打开两个项目

<img src="./images/1.png" style="zoom: 50%;">



加载依赖

<img src="./images/2.png" style="zoom: 33%;">

选择Java版本

<img src="./images/3.png" style="zoom: 33%;">

<img src="./images/4.png" style="zoom: 50%;">



运行：点击MainApplication主类，点击箭头

<img src="./images/5.png" style="zoom: 50%;">





# web-smart-toys-backend-public项目说明

## 接口文档

启动项目后，访问(http://localhost:5001/api/doc.html#/home)

<img src="./images/6.png" style="zoom: 50%;">







# web-smart-toys-backend-internal 项目说明

## 接口文档

启动项目后，访问(http://localhost:5009/api/doc.html#/home)