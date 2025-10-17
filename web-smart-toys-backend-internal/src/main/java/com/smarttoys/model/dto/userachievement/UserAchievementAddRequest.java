package com.smarttoys.model.dto.userachievement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAchievementAddRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;


    /**
     * 成就 id
     */
    private Long achievementId;

    /**
     * 用户 id
     */
    private Long userId;



}
