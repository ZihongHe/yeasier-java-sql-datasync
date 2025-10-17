package com.smarttoys.model.dto.userachievement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAchievementUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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
     * 是否删除
     */
    private Integer isDelete;
}
