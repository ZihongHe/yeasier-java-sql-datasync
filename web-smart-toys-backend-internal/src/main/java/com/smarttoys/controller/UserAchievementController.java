package com.smarttoys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.userachievement.UserAchievementAddRequest;
import com.smarttoys.model.dto.userachievement.UserAchievementDeleteRequest;
import com.smarttoys.model.dto.userachievement.UserAchievementQueryRequest;
import com.smarttoys.model.dto.userachievement.UserAchievementUpdateRequest;
import com.smarttoys.model.entity.Achievement;
import com.smarttoys.model.entity.UserAchievement;
import com.smarttoys.service.AchievementService;
import com.smarttoys.service.UserAchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserAchievementController {

    @Resource
    private UserAchievementService userAchievementService;

    @Resource
    private AchievementService achievementService;

    /**
     * 新增用户成就(用户获得成就)
     */
    @PostMapping("/add_user_achievement")
    public BaseResponse<Long> addUserAchievement(@RequestBody UserAchievementAddRequest userAchievementAddRequest) {
        if (userAchievementAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        UserAchievement userAchievement = new UserAchievement();
        BeanUtils.copyProperties(userAchievementAddRequest, userAchievement);
        // 之前有多少个人获得了该成就
        UserAchievementQueryRequest userAchievementQueryRequest = new UserAchievementQueryRequest();
        userAchievementQueryRequest.setAchievementId(userAchievement.getAchievementId());
        long count = userAchievementService.count(userAchievementService.getQueryWrapper(userAchievementQueryRequest));
        // 设置获得该成就的位次
        userAchievement.setUserRank(count + 1);

        // 校验
        userAchievementService.validUserAchievement(userAchievement, true);

        // 如果该玩家是第一个获得该成就的，同时更新成就表
        if (count == 0) {
            Achievement achievement = achievementService.getById(userAchievement.getAchievementId());
            achievement.setFirstAchievedUserId(userAchievement.getUserId());
            achievementService.updateById(achievement);
        }

        // 保存用户成就
        boolean result = userAchievementService.save(userAchievement);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(userAchievement.getUserAchievementId());
    }

    /**
     * 删除用户成就
     */
    @PostMapping("/delete_user_achievement")
    public BaseResponse<Boolean> deleteUserAchievement(@RequestBody UserAchievementDeleteRequest userAchievementDeleteRequest) {
        if (userAchievementDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long userAchievementId = userAchievementDeleteRequest.getUserAchievementId();
        if (userAchievementId == null || userAchievementId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 校验是否存在
        UserAchievement userAchievement = userAchievementService.getById(userAchievementId);
        ThrowUtils.throwIf(userAchievement == null, ErrorCode.NOT_FOUND_ERROR, "用户成就不存在");
        boolean result = userAchievementService.removeById(userAchievementId);
        return ResultUtils.success(result);
    }

    /**
     * 查询用户成就
     */
    @PostMapping("/query_user_achievement")
    public BaseResponse<Page<UserAchievement>> queryUserAchievement(@RequestBody UserAchievementQueryRequest userAchievementQueryRequest) {
        if (userAchievementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        long current = userAchievementQueryRequest.getCurrent();
        long size = userAchievementQueryRequest.getPageSize();
        Page<UserAchievement> userAchievementPage = userAchievementService.page(new Page<>(current, size), userAchievementService.getQueryWrapper(userAchievementQueryRequest));
        return ResultUtils.success(userAchievementPage);
    }

    /**
     * 更新用户成就
     */
    @PostMapping("/update_user_achievement")
    public BaseResponse<Boolean> updateUserAchievement(@RequestBody UserAchievementUpdateRequest userAchievementUpdateRequest) {
        if (userAchievementUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        UserAchievement userAchievement = new UserAchievement();
        BeanUtils.copyProperties(userAchievementUpdateRequest, userAchievement);
        boolean result = userAchievementService.updateById(userAchievement);
        return ResultUtils.success(result);
    }

    /**
     * 获取用户成就数量
     */
    @PostMapping("/get_user_achievement_count")
    public BaseResponse<Long> getUserAchievementCount(@RequestBody UserAchievementQueryRequest userAchievementQueryRequest) {
        if (userAchievementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        userAchievementQueryRequest.setUserAchievementId(null);
        userAchievementQueryRequest.setAchievementId(null);
        userAchievementQueryRequest.setUserRank(null);

        long count = userAchievementService.count(userAchievementService.getQueryWrapper(userAchievementQueryRequest));
        return ResultUtils.success(count);
    }
}
