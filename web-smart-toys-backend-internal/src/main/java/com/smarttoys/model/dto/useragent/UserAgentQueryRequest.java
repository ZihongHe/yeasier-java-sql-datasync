package com.smarttoys.model.dto.useragent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAgentQueryRequest extends PageRequest implements Serializable {

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
     * 是否删除
     */
    private Integer isDelete;
}
