package com.smarttoys.controller;

import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.user.UserDeleteRequest;
import com.smarttoys.model.dto.useragent.UserAgentAddRequest;
import com.smarttoys.model.dto.useragent.UserAgentDeleteRequest;
import com.smarttoys.model.dto.useragent.UserAgentQueryRequest;
import com.smarttoys.model.dto.useragent.UserAgentUpdateRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.entity.UserAgent;
import com.smarttoys.service.AgentService;
import com.smarttoys.service.UserAgentService;
import com.smarttoys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserAgentController {

    @Resource
    private UserAgentService userAgentService;

    @Resource
    private UserService userService;
    @Resource
    private AgentService agentService;

    /**
     * 新增用户智能体
     */
    @PostMapping("/add_user_agent")
    public BaseResponse<Long> addUserAgent(@RequestBody UserAgentAddRequest userAgentAddRequest) {
        if (userAgentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        UserAgent userAgent = new UserAgent();
        userAgentService.validUserAgent(userAgent, true);
        Long userAgentId = userAgentService.addUserAgent(userAgentAddRequest);
        return ResultUtils.success(userAgentId);
    }
    /**
     * 删除用户智能体
     */
    @PostMapping("/delete_user_agent")
    public BaseResponse<Boolean> deleteUserAgent(@RequestBody UserAgentDeleteRequest userAgentDeleteRequest) {
        if (userAgentDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long userAgentId = userAgentDeleteRequest.getUserAgentId();
        UserAgent userAgent = userAgentService.getById(userAgentId);
        ThrowUtils.throwIf(userAgentId == null || userAgent == null || userAgentId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
        boolean delete = userAgentService.removeById(userAgentId);
        return ResultUtils.success(delete);
    }
    /**
     * 获取用户智能体数量
     */
    @PostMapping("/get_user_agent_count")
    public BaseResponse<Long> getUserAgentCount(@RequestBody UserAgentQueryRequest userAgentQueryRequest) {
        if (userAgentQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long userId = userAgentQueryRequest.getUserId();
        ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");

        UserAgentQueryRequest query = new UserAgentQueryRequest();
        query.setUserId(userId);

        long count = userAgentService.count(userAgentService.getQueryWrapper(query));
        return ResultUtils.success(count);
    }

    /**
     * 更新用户智能体
     */
    @PostMapping("/update_user_agent")
    public BaseResponse<Long> updateUserAgent(@RequestBody UserAgentUpdateRequest userAgentUpdateRequest) {
        if (userAgentUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        UserAgent userAgent = new UserAgent();
        BeanUtils.copyProperties(userAgentUpdateRequest, userAgent);
        boolean update = userAgentService.updateById(userAgent);
        return ResultUtils.success(userAgent.getUserAgentId());
    }

}
