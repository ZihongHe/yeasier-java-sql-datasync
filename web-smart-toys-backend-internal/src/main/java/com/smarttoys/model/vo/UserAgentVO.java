package com.smarttoys.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.UserAgent;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserAgentVO implements Serializable {
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
    private Integer isDelete;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param userAgentVO
     * @return
     */
    public static UserAgent voToObj(UserAgentVO userAgentVO) {
        if (userAgentVO == null) {
            return null;
        }
        UserAgent userAgent = new UserAgent();
        BeanUtils.copyProperties(userAgentVO, userAgent);
        return userAgent;
    }

    /**
     * 对象转包装类
     *
     * @param userAgent
     * @return
     */
    public static UserAgentVO objToVo(UserAgent userAgent) {
        if (userAgent == null) {
            return null;
        }
        UserAgentVO userAgentVO = new UserAgentVO();
        BeanUtils.copyProperties(userAgent, userAgentVO);

        return userAgentVO;
    }


}
