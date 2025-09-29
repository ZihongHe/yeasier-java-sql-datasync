package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.mapper.SandboxMapper;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.service.AgentService;
import com.smarttoys.mapper.AgentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 明月
* @description 针对表【agent(智能体)】的数据库操作Service实现
* @createDate 2025-09-25 17:30:48
*/
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent>
    implements AgentService{

    @Resource
    private AgentMapper agentMapper;

    @Resource
    private SandboxMapper sandboxMapper;

    @Override
    public void validAgent(Agent agent, boolean add) {
        if (agent == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String agentName = agent.getAgentName();
        String agentProfile = agent.getAgentProfile();
        String agentWakeword = agent.getAgentWakeword();
        if(add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(agentName, agentProfile, agentWakeword), ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验沙盒是否存在
        Long sandboxId = agent.getSandboxId();
        if (sandboxId != null){
            Sandbox sandbox = sandboxMapper.selectById(sandboxId);
            ThrowUtils.throwIf(sandbox == null, ErrorCode.SANDBOX_NOT_EXIST, "沙盒不存在");

        }

    }

    @Override
    public Long getAgentSandboxId(long agentId) {
        // 获取智能体所在沙盒
        Agent agent = agentMapper.selectById(agentId);
        return agent.getSandboxId();
    }
}




