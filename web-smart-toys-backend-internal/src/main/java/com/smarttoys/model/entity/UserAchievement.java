package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户成就
 * @TableName user_achievement
 */
@TableName(value ="user_achievement")
@Data
public class UserAchievement {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userAchievementId;

    /**
     * 成就 id
     */
    private Long achievementId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 获得该成就位次
     */
    private Long userRank;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}