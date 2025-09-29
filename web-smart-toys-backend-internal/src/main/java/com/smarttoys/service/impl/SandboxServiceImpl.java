package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.model.dto.sandbox.SandboxAddRequest;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.service.SandboxService;
import com.smarttoys.mapper.SandboxMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 明月
* @description 针对表【sandbox(沙盒)】的数据库操作Service实现
* @createDate 2025-09-25 17:30:41
*/
@Service
public class SandboxServiceImpl extends ServiceImpl<SandboxMapper, Sandbox>
    implements SandboxService{

    @Resource
    private SandboxMapper sandboxMapper;

    @Override
    public Long addSandbox(SandboxAddRequest sandboxAddRequest) {
        String sandboxType = sandboxAddRequest.getSandboxType();
        if (sandboxType == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "沙盒类型为空");
        }
        Sandbox sandbox = new Sandbox();
        sandbox.setSandboxType(sandboxType);
        sandboxMapper.insert(sandbox);
        return sandbox.getSandboxId();
    }
}




