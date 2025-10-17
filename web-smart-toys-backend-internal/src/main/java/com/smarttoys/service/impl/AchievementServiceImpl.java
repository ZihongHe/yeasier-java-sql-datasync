package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.mapper.AchievementMapper;
import com.smarttoys.model.dto.achievement.AchievementAddRequest;
import com.smarttoys.model.dto.achievement.AchievementQueryRequest;
import com.smarttoys.model.entity.Achievement;
import com.smarttoys.service.AchievementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 明月
* @description 针对表【achievement(成就)】的数据库操作Service实现
* @createDate 2025-10-16 16:54:52
*/
@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement>
    implements AchievementService{

    @Override
    public Achievement addAchievement(AchievementAddRequest achievementAddRequest) {
        if (achievementAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementAddRequest, achievement);
        return this.save(achievement) ? achievement : null;
    }

    @Override
    public void validAchievement(Achievement achievement, boolean add) {
        if (achievement == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        String achievementName = achievement.getAchievementName();
        String achievementProfile = achievement.getAchievementProfile();

        // 创建时，参数不能为空
        if(add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(achievementName, achievementProfile), ErrorCode.PARAMS_ERROR, "参数为空");
        }

    }

    @Override
    public QueryWrapper<Achievement> getQueryWrapper(AchievementQueryRequest achievementQueryRequest) {
        if (achievementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long achievementId = achievementQueryRequest.getAchievementId();
        String achievementName = achievementQueryRequest.getAchievementName();
        String achievementProfile = achievementQueryRequest.getAchievementProfile();
        Long firstAchievedUserId = achievementQueryRequest.getFirstAchievedUserId();
        Date createTime = achievementQueryRequest.getCreateTime();
        Integer isDelete = achievementQueryRequest.getIsDelete();
        String sortField = achievementQueryRequest.getSortField();
        String sortOrder = achievementQueryRequest.getSortOrder();

        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(achievementId != null && achievementId > 0, "achievementId", achievementId);
        queryWrapper.eq(achievementName != null, "achievementName", achievementName);
        queryWrapper.like(achievementProfile != null, "achievementProfile", achievementProfile);
        queryWrapper.eq(firstAchievedUserId != null && firstAchievedUserId > 0, "firstAchievedUserId", firstAchievedUserId);
        queryWrapper.ge(createTime != null, "createTime", createTime);
        queryWrapper.eq(isDelete != null, "isDelete", isDelete);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




