package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.model.dto.usersandbox.UserSandboxAddRequest;
import com.smarttoys.model.dto.usersandbox.UserSandboxQueryRequest;
import com.smarttoys.model.entity.UserSandbox;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 明月
* @description 针对表【user_sandbox(沙盒)】的数据库操作Service
* @createDate 2025-10-17 10:38:31
*/
public interface UserSandboxService extends IService<UserSandbox> {

    /**
     * 校验
     *
     * @param userSandbox
     * @param add
     */
    void validUserSandbox(UserSandbox userSandbox, boolean add);

    /**
     * 新增用户沙盒
     *
     * @param userSandboxAddRequest
     */
    Long addUserSandbox(UserSandboxAddRequest userSandboxAddRequest);

    /**
     * 获取查询包装类
     *
     * @param userSandboxQueryRequest
     * @return
     */
    QueryWrapper<UserSandbox> getQueryWrapper(UserSandboxQueryRequest userSandboxQueryRequest);
}
