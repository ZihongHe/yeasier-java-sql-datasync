package com.smarttoys.model.vo;

import com.smarttoys.model.entity.Agent;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AgentVO implements Serializable {
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
     * 创建人信息
     */
    private UserVO user;

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
     * @param agentVO
     * @return
     */
    public static Agent voToObj(AgentVO agentVO) {
        if (agentVO == null) {
            return null;
        }
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentVO, agent);
        return agent;
    }

    /**
     * 对象转包装类
     *
     * @param agent
     * @return
     */
    public static AgentVO objToVo(Agent agent) {
        if (agent == null) {
            return null;
        }
        AgentVO agentVO = new AgentVO();
        BeanUtils.copyProperties(agent, agentVO);

        return agentVO;
    }

    /**
     * 列表转包装类
     *
     * @param agentList
     * @return
     */
    public static List<AgentVO> objListToVoList(List<Agent> agentList) {
        List<AgentVO> agentVOList = new ArrayList<>() ;
        for (Agent agent : agentList) {
            agentVOList.add(objToVo(agent));
        }
        return agentVOList;
    }
}
