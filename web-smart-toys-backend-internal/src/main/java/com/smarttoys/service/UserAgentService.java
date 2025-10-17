package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.model.dto.useragent.UserAgentAddRequest;
import com.smarttoys.model.dto.useragent.UserAgentQueryRequest;
import com.smarttoys.model.entity.UserAgent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 明月
* @description 针对表【user_agent(用户智能体)】的数据库操作Service
* @createDate 2025-10-17 10:38:31
*/
public interface UserAgentService extends IService<UserAgent> {

    /**
     * 校验
     */
    void validUserAgent(UserAgent userAgent, boolean add);

    /**
     * 新增用户智能体
     */
    Long addUserAgent(UserAgentAddRequest userAgentAddRequest);

    /**
     * 获取查询包装类
     */
    QueryWrapper<UserAgent> getQueryWrapper(UserAgentQueryRequest userAgentQueryRequest);
}
