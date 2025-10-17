package com.smarttoys.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.Achievement;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 成就视图（脱敏）
 *
 
 */
@Data
public class AchievementVO implements Serializable {
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
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public static AchievementVO objToVo(Achievement achievement) {
        if (achievement == null) {
            return null;
        }
        AchievementVO achievementVO = new AchievementVO();
        BeanUtils.copyProperties(achievement, achievementVO);
        return achievementVO;
    }
    public static Achievement voToObj(AchievementVO achievementVO) {
        if (achievementVO == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementVO, achievement);
        return achievement;
    }
}