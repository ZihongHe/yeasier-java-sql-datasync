package com.smarttoys.model.dto.achievement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AchievementAddRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 成就名称
     */
    private String achievementName;

    /**
     * 成就描述
     */
    private String achievementProfile;

}
