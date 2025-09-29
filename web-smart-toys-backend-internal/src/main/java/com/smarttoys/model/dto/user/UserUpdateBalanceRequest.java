package com.smarttoys.model.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserUpdateBalanceRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 更新的配额额度
     */
    private BigDecimal balanceQuota;
    private static final long serialVersionUID = 1L;
}
