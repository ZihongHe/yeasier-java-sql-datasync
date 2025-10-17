package com.smarttoys.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.entity.UserAchievement;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户成就视图（脱敏）
 *
 
 */
@Data
public class UserAchievementVO implements Serializable {
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

    public static UserAchievementVO objToVo(UserAchievement userAchievement) {
        if (userAchievement == null) {
            return null;
        }
        UserAchievementVO userAchievementVO = new UserAchievementVO();
        BeanUtils.copyProperties(userAchievement, userAchievementVO);
        return userAchievementVO;
    }
    public static UserAchievement voToObj(UserAchievementVO userAchievementVO) {
        if (userAchievementVO == null) {
            return null;
        }
        UserAchievement userAchievement = new UserAchievement();
        BeanUtils.copyProperties(userAchievementVO, userAchievement);
        return userAchievement;
    }
}