package com.smarttoys.model.dto.achievement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class AchievementUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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
     * 是否删除
     */
    private Integer isDelete;
}
