package com.smarttoys.model.dto.useragent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAgentUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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
     * 所属沙盒 id
     */
    private Long sandboxId;

    /**
     * 在线状态：0-离线 1-在线
     */
    private Integer onlineStatus;


    /**
     * 是否删除
     */
    private Integer isDelete;
}
