package com.smarttoys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.sandbox.SandboxAddRequest;
import com.smarttoys.model.dto.sandbox.SandboxQueryRequest;
import com.smarttoys.model.dto.sandbox.SandboxUpdateRequest;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.service.SandboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class SandboxController {

    @Resource
    private SandboxService sandboxService;

    /**
     * 新增沙盒
     */
    @PostMapping("/add_sandbox")
    public BaseResponse<Long> addSandbox(@RequestBody SandboxAddRequest sandboxAddRequest) {
        if (sandboxAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验
        sandboxService.validSandbox(sandboxAddRequest, true);
        Sandbox sandbox = new Sandbox();
        BeanUtils.copyProperties(sandboxAddRequest, sandbox);

        return ResultUtils.success(sandboxService.addSandbox(sandboxAddRequest));
    }

    /**
     * 按条件查询沙盒
     */
    @PostMapping("/query_sandbox")
    public BaseResponse<Page<Sandbox>> querySandbox(@RequestBody SandboxQueryRequest sandboxQueryRequest) {
        if (sandboxQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long current = sandboxQueryRequest.getCurrent();
        long size = sandboxQueryRequest.getPageSize();
        Page<Sandbox> sandboxPage = sandboxService.page(new Page<>(current, size), sandboxService.getQueryWrapper(sandboxQueryRequest));
        return ResultUtils.success(sandboxPage);
    }

    /**
     * 删除沙盒
     */
    @PostMapping("/delete_sandbox")
    public BaseResponse<Boolean> deleteSandbox(@RequestBody SandboxQueryRequest sandboxQueryRequest) {
        if (sandboxQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验沙盒是否存在
        long sandboxId = sandboxQueryRequest.getSandboxId();
        Sandbox oldSandbox = sandboxService.getById(sandboxId);
        ThrowUtils.throwIf(oldSandbox == null, ErrorCode.SANDBOX_NOT_EXIST, "沙盒不存在");

        return ResultUtils.success(sandboxService.removeById(sandboxId));
    }

    /**
     * 更新沙盒
     */
    @PostMapping("/update_sandbox")
    public BaseResponse<Long> updateSandbox(@RequestBody SandboxUpdateRequest sandboxUpdateRequest) {
        if (sandboxUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验沙盒是否存在
        long sandboxId = sandboxUpdateRequest.getSandboxId();
        Sandbox oldSandbox = sandboxService.getById(sandboxId);
        ThrowUtils.throwIf(oldSandbox == null, ErrorCode.SANDBOX_NOT_EXIST, "沙盒不存在");

        Sandbox sandbox = new Sandbox();
        BeanUtils.copyProperties(sandboxUpdateRequest, sandbox);
        sandboxService.updateById(sandbox);
        return ResultUtils.success(sandbox.getSandboxId());
    }
}
