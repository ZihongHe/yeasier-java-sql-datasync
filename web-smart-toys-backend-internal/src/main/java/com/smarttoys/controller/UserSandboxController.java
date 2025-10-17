package com.smarttoys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.usersandbox.UserSandboxAddRequest;
import com.smarttoys.model.dto.usersandbox.UserSandboxDeleteRequest;
import com.smarttoys.model.dto.usersandbox.UserSandboxQueryRequest;
import com.smarttoys.model.dto.usersandbox.UserSandboxUpdateRequest;
import com.smarttoys.model.entity.UserSandbox;
import com.smarttoys.service.UserSandboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserSandboxController {

    @Resource
    private UserSandboxService userSandboxService;

    /**
     * 新增用户沙盒
     */
    @PostMapping("/add_user_sandbox")
    public BaseResponse<Long> addUserSandbox(@RequestBody UserSandboxAddRequest userSandboxAddRequest){
        if (userSandboxAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        UserSandbox userSandbox = new UserSandbox();
        BeanUtils.copyProperties(userSandboxAddRequest, userSandbox);
        userSandboxService.validUserSandbox(userSandbox, true);
        Long userSandboxId = userSandboxService.addUserSandbox(userSandboxAddRequest);
        return ResultUtils.success(userSandboxId);
    }

    /**
     * 删除用户沙盒
     */
    @PostMapping("/delete_user_sandbox")
    public BaseResponse<Boolean> deleteUserSandbox(@RequestBody UserSandboxDeleteRequest userSandboxDeleteRequest) {
        if (userSandboxDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        ThrowUtils.throwIf(userSandboxDeleteRequest.getUserSandboxId() == null, ErrorCode.PARAMS_ERROR, "参数为空");

        UserSandbox oldUserSandbox = userSandboxService.getById(userSandboxDeleteRequest.getUserSandboxId());
        ThrowUtils.throwIf(oldUserSandbox == null, ErrorCode.PARAMS_ERROR, "用户沙盒不存在");

        boolean delete = userSandboxService.removeById(userSandboxDeleteRequest.getUserSandboxId());
        return ResultUtils.success(delete);
    }

    /**
     * 查询用户沙盒
     */
    @PostMapping("/query_user_sandbox")
    public BaseResponse<Page<UserSandbox>> queryUserSandbox(@RequestBody UserSandboxQueryRequest userSandboxQueryRequest) {
        if (userSandboxQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long current = userSandboxQueryRequest.getCurrent();
        long pageSize = userSandboxQueryRequest.getPageSize();

        Page<UserSandbox> page = userSandboxService.page(new Page<>(current, pageSize), userSandboxService.getQueryWrapper(userSandboxQueryRequest));
        return ResultUtils.success(page);
    }

    /**
     * 更新用户沙盒
     */
    @PostMapping("/update_user_sandbox")
    public BaseResponse<Long> updateUserSandbox(@RequestBody UserSandboxUpdateRequest userSandboxUpdateRequest) {
        if (userSandboxUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        ThrowUtils.throwIf(userSandboxUpdateRequest.getUserSandboxId() == null, ErrorCode.PARAMS_ERROR, "参数为空");
        UserSandbox userSandbox = new UserSandbox();
        BeanUtils.copyProperties(userSandboxUpdateRequest, userSandbox);
        boolean update = userSandboxService.updateById(userSandbox);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败");
        }
        return ResultUtils.success(userSandbox.getUserSandboxId());
    }
}
