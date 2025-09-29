package com.smarttoys.controller;

import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.agent.AgentAddRequest;
import com.smarttoys.model.dto.agent.AgentQueryRequest;
import com.smarttoys.model.dto.agent.AgentUpdateRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.User;
import com.smarttoys.service.AgentService;
import com.smarttoys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AgentController {

    @Resource
    private AgentService agentService;

    @Resource
    private UserService userService;


    @PostMapping("/add_agent")
    public BaseResponse<Long> addAgent(@RequestBody AgentAddRequest agentAddRequest, HttpServletRequest request) {
        if (agentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentAddRequest, agent);
        agentService.validAgent(agent, true);
        User loginUser = userService.getLoginUser(request);
        agent.setUserId(loginUser.getUserId());
        boolean result = agentService.save(agent);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(agent.getAgentId());
    }

    /**
     * 更新智能体
     * @param agentUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update_agent_virtual")
    public BaseResponse<Long> updateAgentVirtual(@RequestBody AgentUpdateRequest agentUpdateRequest, HttpServletRequest request) {
        if (agentUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentUpdateRequest, agent);
        long agentId = agentUpdateRequest.getAgentId();
        Agent oldAgent = agentService.getById(agentId);
        ThrowUtils.throwIf(oldAgent == null, ErrorCode.AGENT_NOT_EXIST,"智能体不存在");
        agentService.validAgent(agent, false);
        boolean result = agentService.updateById(agent);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(agentId);
    }

    /**
     * 查询智能体所在沙盒
     */
    @PostMapping("/query_agent_sandbox")
    public BaseResponse<Long> queryAgentSandbox(long agentId) {
        if (agentId == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Agent oldAgent = agentService.getById(agentId);
        ThrowUtils.throwIf(oldAgent == null, ErrorCode.AGENT_NOT_EXIST,"智能体不存在");
        return ResultUtils.success(agentService.getAgentSandboxId(agentId));
    }

}
