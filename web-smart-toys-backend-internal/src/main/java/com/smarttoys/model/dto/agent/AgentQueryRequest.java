package com.smarttoys.model.dto.agent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AgentQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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
     * 是否删除
     */
    private Integer isDelete;
}
