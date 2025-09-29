package com.smarttoys.model.dto.agent;

import com.smarttoys.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
     * 是否删除
     */
    private Integer isDelete;
}
