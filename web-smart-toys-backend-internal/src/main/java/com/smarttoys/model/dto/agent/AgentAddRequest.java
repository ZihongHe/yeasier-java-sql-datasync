package com.smarttoys.model.dto.agent;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AgentAddRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;


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

}
