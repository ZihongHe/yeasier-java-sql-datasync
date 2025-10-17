package com.smarttoys.model.dto.useragent;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAgentAddRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

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



}
