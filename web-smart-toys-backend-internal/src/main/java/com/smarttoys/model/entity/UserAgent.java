package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户智能体
 * @TableName user_agent
 */
@TableName(value ="user_agent")
@Data
public class UserAgent {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userAgentId;

    /**
     * 智能体名称
     */
    private String userAgentName;

    /**
     * 智能体描述
     */
    private String userAgentProfile;

    /**
     * 智能体唤醒词
     */
    private String userAgentWakeword;

    /**
     * 智能体记忆
     */
    private String userAgentMemory;

    /**
     * 智能体位置信息
     */
    private String userAgentPosition;

    /**
     * 智能体当前正在做的行为
     */
    private String userAgentAction;

    /**
     * 所属用户 id
     */
    private Long userId;

    /**
     * 智能体 id
     */
    private Long agentId;

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