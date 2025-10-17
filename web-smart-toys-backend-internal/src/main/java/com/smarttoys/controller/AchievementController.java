package com.smarttoys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.achievement.AchievementAddRequest;
import com.smarttoys.model.dto.achievement.AchievementDeleteRequest;
import com.smarttoys.model.dto.achievement.AchievementQueryRequest;
import com.smarttoys.model.dto.achievement.AchievementUpdateRequest;
import com.smarttoys.model.entity.Achievement;
import com.smarttoys.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class AchievementController {

    @Resource
    private AchievementService achievementService;

    /**
     * 新增成就
     */
    @PostMapping("/add_achievement")
    public BaseResponse<Long> addAchievement(@RequestBody AchievementAddRequest achievementAddRequest) {
        if (achievementAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementAddRequest, achievement);
        boolean result = achievementService.save(achievement);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        Long achievementId = achievement.getAchievementId();
        return ResultUtils.success(achievementId);
    }

    /**
     * 更新成就
     */
    @PostMapping("/update_achievement")
    public BaseResponse<Long> updateAchievement(@RequestBody AchievementUpdateRequest achievementUpdateRequest) {
        if (achievementUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验成就是否存在
        Long achievementId = achievementUpdateRequest.getAchievementId();
        Achievement oldAchievement = achievementService.getById(achievementId);
        ThrowUtils.throwIf(oldAchievement == null, ErrorCode.ACHIEVEMENT_NOT_EXIST, "成就不存在");

        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementUpdateRequest, achievement);
        boolean result = achievementService.updateById(achievement);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(achievementId);
    }

    /**
     * 删除成就
     */
    @PostMapping("/delete_achievement")
    public BaseResponse<Boolean> deleteAchievement(@RequestBody AchievementDeleteRequest achievementDeleteRequest) {
        if (achievementDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验成就是否存在
        Long achievementId = achievementDeleteRequest.getAchievementId();
        Achievement oldAchievement = achievementService.getById(achievementId);
        ThrowUtils.throwIf(oldAchievement == null, ErrorCode.ACHIEVEMENT_NOT_EXIST, "成就不存在");

        return ResultUtils.success(achievementService.removeById(achievementId));
    }

    /**
     * 按条件查询成就
     */
    @PostMapping("/query_achievement")
    public BaseResponse<Page<Achievement>> queryAchievement(@RequestBody AchievementQueryRequest achievementQueryRequest) {
        if (achievementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long current = achievementQueryRequest.getCurrent();
        long pageSize = achievementQueryRequest.getPageSize();
        Page<Achievement> page = achievementService.page(new Page<>(current, pageSize), achievementService.getQueryWrapper(achievementQueryRequest));
        return ResultUtils.success(page);

    }
}
