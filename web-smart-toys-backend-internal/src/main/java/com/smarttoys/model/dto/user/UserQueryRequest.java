package com.smarttoys.model.dto.user;

import com.smarttoys.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     *  id
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

    private static final long serialVersionUID = 1L;
}