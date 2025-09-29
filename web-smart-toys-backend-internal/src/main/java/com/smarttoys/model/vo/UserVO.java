package com.smarttoys.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 用户视图（脱敏）
 *
 
 */
@Data
public class UserVO implements Serializable {
    /**
     * id
     */
    private Long userId;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户余额
     */
    private BigDecimal userBalance;
    /*
     * 默认agentId
     */
    private Long defaultAgentId;

    /**
     * 创建的智能体
     */
    private List<AgentVO> agents;

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

    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        return user;
    }
}