package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 智能体
 * @TableName agent
 */
@TableName(value ="agent")
@Data
public class Agent {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long agentId;

    /**
     * 智能体名称
     */
    private String agentName;

    /**
     * 智能体描述
     */
    private String agentProfile;

    /**
     * 智能体唤醒词
     */
    private String agentWakeword;

    /**
     * 智能体记忆
     */
    private String agentMemory;

    /**
     * 智能体位置信息
     */
    private String agentPosition;

    /**
     * 智能体当前正在做的行为
     */
    private String agentAction;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 所属沙盒 id
     */
    private Long sandboxId;

    /**
     * 在线状态：0-离线 1-在线
     */
    private Integer onlineStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}