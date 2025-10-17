package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.model.dto.sandbox.SandboxAddRequest;
import com.smarttoys.model.dto.sandbox.SandboxQueryRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.entity.Sandbox;

/**
* @author 明月
* @description 针对表【sandbox(沙盒)】的数据库操作Service
* @createDate 2025-09-25 17:30:41
*/
public interface SandboxService extends IService<Sandbox> {

    /**
     * 新增沙盒
     *
     * @param sandboxAddRequest
     * @return sandboxId
     */
    Long addSandbox(SandboxAddRequest sandboxAddRequest);

    /**
     * 获取查询包装类
     *
     */
    QueryWrapper<Sandbox> getQueryWrapper(SandboxQueryRequest sandboxQueryRequest);

    /**
     * 校验
     *
     */
    void validSandbox(SandboxAddRequest sandboxAddRequest, boolean add);
}
