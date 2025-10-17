package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.mapper.SandboxMapper;
import com.smarttoys.model.dto.sandbox.SandboxAddRequest;
import com.smarttoys.model.dto.sandbox.SandboxQueryRequest;
import com.smarttoys.model.entity.Sandbox;
import com.smarttoys.service.SandboxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
        String sandboxName = sandboxAddRequest.getSandboxName();
        if (sandboxType == null || sandboxName == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "沙盒类型或名称为空");
        }
        Sandbox sandbox = new Sandbox();
        sandbox.setSandboxType(sandboxType);
        sandbox.setSandboxName(sandboxName);
        sandboxMapper.insert(sandbox);
        return sandbox.getSandboxId();
    }

    @Override
    public QueryWrapper<Sandbox> getQueryWrapper(SandboxQueryRequest sandboxQueryRequest) {
        QueryWrapper<Sandbox> queryWrapper = new QueryWrapper<>();
        if (sandboxQueryRequest == null){
            return queryWrapper;
        }

        Long sandboxId = sandboxQueryRequest.getSandboxId();
        String sandboxName = sandboxQueryRequest.getSandboxName();
        String sandboxType = sandboxQueryRequest.getSandboxType();
        Long userId = sandboxQueryRequest.getUserId();
        Date createTime = sandboxQueryRequest.getCreateTime();
        Integer isDelete = sandboxQueryRequest.getIsDelete();

        String sortField = sandboxQueryRequest.getSortField();
        String sortOrder = sandboxQueryRequest.getSortOrder();


        queryWrapper.eq(sandboxId != null, "sandboxId", sandboxId);
        queryWrapper.eq(sandboxType != null, "sandboxType", sandboxType);
        queryWrapper.like(StringUtils.isNotBlank(sandboxName), "sandboxName", sandboxName);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.eq(isDelete != null, "isDelete", isDelete);
        queryWrapper.ge(createTime != null, "createTime", createTime);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public void validSandbox(SandboxAddRequest sandboxAddRequest, boolean add) {
        if (sandboxAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String sandboxName = sandboxAddRequest.getSandboxName();
        String sandboxType = sandboxAddRequest.getSandboxType();

        if (add) {
            if (StringUtils.isAnyBlank(sandboxName, sandboxType)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
            }

        }

    }
}




