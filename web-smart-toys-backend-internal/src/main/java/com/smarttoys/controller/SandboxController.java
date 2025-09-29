package com.smarttoys.controller;

import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.model.dto.sandbox.SandboxAddRequest;
import com.smarttoys.service.SandboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        return ResultUtils.success(sandboxService.addSandbox(sandboxAddRequest));
    }
}
