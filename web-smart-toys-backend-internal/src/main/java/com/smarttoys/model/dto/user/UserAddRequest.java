package com.smarttoys.model.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户注册请求体
 *
 
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String userEmail;

    private BigDecimal userBalance;
}
