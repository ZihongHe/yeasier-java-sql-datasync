package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.dto.userachievement.UserAchievementAddRequest;
import com.smarttoys.model.dto.userachievement.UserAchievementQueryRequest;
import com.smarttoys.model.entity.UserAchievement;

/**
* @author 明月
* @description 针对表【userachievement(用户成就)】的数据库操作Service
* @createDate 2025-10-16 16:54:57
*/
public interface UserAchievementService extends IService<UserAchievement> {

    /**
     * 校验
     */
    void validUserAchievement(UserAchievement userAchievement, boolean add);

    /**
     * 获取查询包装类
     */
    QueryWrapper<UserAchievement> getQueryWrapper(UserAchievementQueryRequest userAchievementQueryRequest);

}
