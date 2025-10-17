package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.model.dto.agent.AgentQueryRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.entity.Agent;

/**
* @author 明月
* @description 针对表【agent(智能体)】的数据库操作Service
* @createDate 2025-09-25 17:30:48
*/
public interface AgentService extends IService<Agent> {

    /**
     * 校验
     *
     * @param agent
     * @param add
     */
    void validAgent(Agent agent, boolean add);

    /**
     * 获取智能体所在沙盒id
     * @param agentId
     * @return
     */
    Long getAgentSandboxId(long agentId);

    /**
     * 查询包装类
     */
    QueryWrapper<Agent> getQueryWrapper(AgentQueryRequest agentQueryRequest);

}
