package com.smarttoys.model.dto.achievement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class AchievementDeleteRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * id
     */
    private Long achievementId;

}
