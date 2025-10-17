package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 成就
 * @TableName achievement
 */
@TableName(value ="achievement")
@Data
public class Achievement {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long achievementId;

    /**
     * 成就名称
     */
    private String achievementName;

    /**
     * 成就描述
     */
    private String achievementProfile;

    /**
     * 首次获得该成就的用户 id
     */
    private Long firstAchievedUserId;

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