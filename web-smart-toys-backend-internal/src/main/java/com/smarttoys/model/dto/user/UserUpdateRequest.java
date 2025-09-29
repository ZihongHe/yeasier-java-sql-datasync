package com.smarttoys.model.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户更新请求
 *
 
 */
@Data
public class UserUpdateRequest implements Serializable {
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

    private String userEmail;
    private BigDecimal userBalance;


    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}