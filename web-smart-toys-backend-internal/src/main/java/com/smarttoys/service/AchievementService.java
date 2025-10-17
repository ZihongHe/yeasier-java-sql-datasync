package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.model.dto.achievement.AchievementAddRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.dto.achievement.AchievementQueryRequest;
import com.smarttoys.model.entity.Achievement;

/**
* @author 明月
* @description 针对表【achievement(成就)】的数据库操作Service
* @createDate 2025-10-16 16:54:52
*/
public interface AchievementService extends IService<Achievement> {

    /**
     * 新增成就
     */
    Achievement addAchievement(AchievementAddRequest achievementAddRequest);

    /**
     * 校验
     */
    void validAchievement(Achievement achievement, boolean add);

    /**
     * 查询包装类
     */
    QueryWrapper<Achievement> getQueryWrapper(AchievementQueryRequest achievementQueryRequest);
}
