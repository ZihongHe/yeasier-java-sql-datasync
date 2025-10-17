package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.useragent.UserAgentAddRequest;
import com.smarttoys.model.dto.useragent.UserAgentQueryRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.entity.UserAgent;
import com.smarttoys.service.AgentService;
import com.smarttoys.service.UserAgentService;
import com.smarttoys.mapper.UserAgentMapper;
import com.smarttoys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 明月
* @description 针对表【user_agent(用户智能体)】的数据库操作Service实现
* @createDate 2025-10-17 10:38:31
*/
@Service
public class UserAgentServiceImpl extends ServiceImpl<UserAgentMapper, UserAgent>
    implements UserAgentService{

    @Resource
    private UserService userService;
    @Resource
    private AgentService agentService;

    @Override
    public void validUserAgent(UserAgent userAgent, boolean add) {
        if (userAgent == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        Long userId = userAgent.getUserId();
        Long agentId = userAgent.getAgentId();

        if (add) {
            ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
            ThrowUtils.throwIf(agentId == null || agentId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
        }


    }

    @Override
    public Long addUserAgent(UserAgentAddRequest userAgentAddRequest) {
        if (userAgentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long userId = userAgentAddRequest.getUserId();
        Long agentId = userAgentAddRequest.getAgentId();

        // 校验用户和智能体是否存在
        User oldUser = userService.getById(userId);
        ThrowUtils.throwIf(oldUser == null, ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        Agent oldAgent = agentService.getById(agentId);
        ThrowUtils.throwIf(oldAgent == null, ErrorCode.NOT_FOUND_ERROR, "智能体不存在");

        UserAgent userAgent = new UserAgent();
        userAgent.setUserAgentName(oldAgent.getAgentName());
        userAgent.setUserAgentProfile(oldAgent.getAgentProfile());
        userAgent.setUserAgentWakeword(oldAgent.getAgentWakeword());
        userAgent.setUserAgentMemory(oldAgent.getAgentMemory());
        userAgent.setUserAgentPosition(oldAgent.getAgentPosition());
        userAgent.setUserAgentAction(oldAgent.getAgentAction());
        userAgent.setUserId(userId);
        userAgent.setAgentId(oldAgent.getAgentId());
        userAgent.setSandboxId(oldAgent.getSandboxId());
        userAgent.setOnlineStatus(0);

        Long newId = (long) this.baseMapper.insert(userAgent);
        return newId;

    }

    @Override
    public QueryWrapper<UserAgent> getQueryWrapper(UserAgentQueryRequest userAgentQueryRequest) {
        if (userAgentQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long userAgentId = userAgentQueryRequest.getUserAgentId();
        String userAgentName = userAgentQueryRequest.getUserAgentName();
        String userAgentProfile = userAgentQueryRequest.getUserAgentProfile();
        String userAgentWakeword = userAgentQueryRequest.getUserAgentWakeword();
        Long userId = userAgentQueryRequest.getUserId();
        Long agentId = userAgentQueryRequest.getAgentId();
        Long sandboxId = userAgentQueryRequest.getSandboxId();
        Integer onlineStatus = userAgentQueryRequest.getOnlineStatus();
        Date createTime = userAgentQueryRequest.getCreateTime();
        Integer isDelete = userAgentQueryRequest.getIsDelete();
        String sortField = userAgentQueryRequest.getSortField();
        String sortOrder = userAgentQueryRequest.getSortOrder();

        QueryWrapper<UserAgent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userAgentId != null && userAgentId > 0, "userAgentId", userAgentId);
        queryWrapper.like(userAgentName != null, "userAgentName", userAgentName);
        queryWrapper.like(userAgentProfile != null, "userAgentProfile", userAgentProfile);
        queryWrapper.like(userAgentWakeword != null, "userAgentWakeword", userAgentWakeword);
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        queryWrapper.eq(agentId != null && agentId > 0, "agentId", agentId);
        queryWrapper.eq(sandboxId != null && sandboxId > 0, "sandboxId", sandboxId);
        queryWrapper.eq(onlineStatus != null, "onlineStatus", onlineStatus);
        queryWrapper.ge(createTime != null, "createTime", createTime);
        queryWrapper.eq(isDelete != null, "isDelete", isDelete);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        return queryWrapper;
    }
}




