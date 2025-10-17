package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.usersandbox.UserSandboxAddRequest;
import com.smarttoys.model.dto.usersandbox.UserSandboxQueryRequest;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.entity.UserSandbox;
import com.smarttoys.service.SandboxService;
import com.smarttoys.service.UserSandboxService;
import com.smarttoys.mapper.UserSandboxMapper;
import com.smarttoys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 明月
* @description 针对表【user_sandbox(沙盒)】的数据库操作Service实现
* @createDate 2025-10-17 10:38:31
*/
@Service
public class UserSandboxServiceImpl extends ServiceImpl<UserSandboxMapper, UserSandbox>
    implements UserSandboxService{

    @Resource
    private UserService userService;
    @Resource
    private SandboxService sandboxService;

    @Override
    public void validUserSandbox(UserSandbox userSandbox, boolean add) {
        if (userSandbox == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        Long userId = userSandbox.getUserId();
        Long sandboxId = userSandbox.getSandboxId();

        if (add) {
            ThrowUtils.throwIf(userId <= 0, ErrorCode.USER_NOT_EXIST, "用户不存在");
            ThrowUtils.throwIf(sandboxId <= 0, ErrorCode.SANDBOX_NOT_EXIST, "沙盒不存在");
        }

    }

    @Override
    public Long addUserSandbox(UserSandboxAddRequest userSandboxAddRequest) {
        if (userSandboxAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        Long userId = userSandboxAddRequest.getUserId();
        Long sandboxId = userSandboxAddRequest.getSandboxId();

        User oldUser = userService.getById(userId);
        ThrowUtils.throwIf(oldUser == null, ErrorCode.USER_NOT_EXIST, "用户不存在");
        Sandbox oldSandbox = sandboxService.getById(sandboxId);
        ThrowUtils.throwIf(oldSandbox == null, ErrorCode.SANDBOX_NOT_EXIST, "沙盒不存在");

        UserSandbox userSandbox = new UserSandbox();

        userSandbox.setSandboxName(oldSandbox.getSandboxName());
        userSandbox.setSandboxType(oldSandbox.getSandboxType());
        userSandbox.setUserId(userId);
        userSandbox.setSandboxId(sandboxId);

        Long newId = (long) this.baseMapper.insert(userSandbox);
        return newId;

    }

    @Override
    public QueryWrapper<UserSandbox> getQueryWrapper(UserSandboxQueryRequest userSandboxQueryRequest) {
        if (userSandboxQueryRequest == null) {
            return null;
        }
        QueryWrapper<UserSandbox> queryWrapper = new QueryWrapper<>();
        Long sandboxId = userSandboxQueryRequest.getSandboxId();
        String sandboxName = userSandboxQueryRequest.getSandboxName();
        String sandboxType = userSandboxQueryRequest.getSandboxType();
        Long userId = userSandboxQueryRequest.getUserId();
        Date createTime = userSandboxQueryRequest.getCreateTime();
        Integer isDelete = userSandboxQueryRequest.getIsDelete();
        String sortField = userSandboxQueryRequest.getSortField();
        String sortOrder = userSandboxQueryRequest.getSortOrder();

        queryWrapper.eq(sandboxId != null && sandboxId > 0, "sandboxId", sandboxId);
        queryWrapper.like(sandboxName != null, "sandboxName", sandboxName);
        queryWrapper.eq(sandboxType != null, "sandboxType", sandboxType);
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        queryWrapper.ge(createTime != null, "createTime", createTime);
        queryWrapper.eq(isDelete != null, "isDelete", isDelete);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




