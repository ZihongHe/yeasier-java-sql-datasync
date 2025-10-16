package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.mapper.SandboxMapper;
import com.smarttoys.model.dto.agent.AgentQueryRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.service.AgentService;
import com.smarttoys.mapper.AgentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public QueryWrapper<Agent> getQueryWrapper(AgentQueryRequest agentQueryRequest) {
        QueryWrapper<Agent> queryWrapper = new QueryWrapper<>();
        if (agentQueryRequest == null) {
            return queryWrapper;
        }
        Long agentId = agentQueryRequest.getAgentId();
        String agentName = agentQueryRequest.getAgentName();
        Long userId = agentQueryRequest.getUserId();
        Long sandboxId = agentQueryRequest.getSandboxId();
        Integer onlineStatus = agentQueryRequest.getOnlineStatus();
        Integer isDelete = agentQueryRequest.getIsDelete();

        queryWrapper.eq(agentId != null && agentId > 0, "id", agentId);
        queryWrapper.like(StringUtils.isNotBlank(agentName), "agentName", agentName);
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        queryWrapper.eq(sandboxId != null && sandboxId > 0, "sandboxId", sandboxId);
        queryWrapper.eq(onlineStatus != null, "onlineStatus", onlineStatus);
        queryWrapper.eq("isDelete", 0);

        return queryWrapper;
    }



}




