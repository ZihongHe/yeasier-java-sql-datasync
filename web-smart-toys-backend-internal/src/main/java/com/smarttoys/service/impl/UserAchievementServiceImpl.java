package com.smarttoys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.mapper.UserAchievementMapper;
import com.smarttoys.model.dto.userachievement.UserAchievementQueryRequest;
import com.smarttoys.model.entity.UserAchievement;
import com.smarttoys.service.UserAchievementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 明月
* @description 针对表【userachievement(用户成就)】的数据库操作Service实现
* @createDate 2025-10-16 16:54:57
*/
@Service
public class UserAchievementServiceImpl extends ServiceImpl<UserAchievementMapper, UserAchievement>
    implements UserAchievementService {

    @Override
    public void validUserAchievement(UserAchievement userAchievement, boolean add) {

        if (userAchievement == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long achievementId = userAchievement.getAchievementId();
        Long userId = userAchievement.getUserId();
        Long userRank = userAchievement.getUserRank();

        if (add) {
            ThrowUtils.throwIf(achievementId == null || achievementId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
            ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
            ThrowUtils.throwIf(userRank == null || userRank <= 0, ErrorCode.PARAMS_ERROR, "参数为空");
        }

    }

    @Override
    public QueryWrapper<UserAchievement> getQueryWrapper(UserAchievementQueryRequest userAchievementQueryRequest) {
        if (userAchievementQueryRequest == null) {
            return null;
        }
        Long userAchievementId = userAchievementQueryRequest.getUserAchievementId();
        Long achievementId = userAchievementQueryRequest.getAchievementId();
        Long userId = userAchievementQueryRequest.getUserId();
        Long userRank = userAchievementQueryRequest.getUserRank();
        Date createTime = userAchievementQueryRequest.getCreateTime();
        Integer isDelete = userAchievementQueryRequest.getIsDelete();
        String sortField = userAchievementQueryRequest.getSortField();
        String sortOrder = userAchievementQueryRequest.getSortOrder();

        QueryWrapper<UserAchievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userAchievementId != null && userAchievementId > 0, "userAchievementId", userAchievementId);
        queryWrapper.eq(achievementId != null && achievementId > 0, "achievementId", achievementId);
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        queryWrapper.eq(userRank != null && userRank > 0, "userRank", userRank);
        queryWrapper.ge(createTime != null, "createTime", createTime);
        queryWrapper.eq(isDelete != null, "isDelete", isDelete);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;

    }
}




