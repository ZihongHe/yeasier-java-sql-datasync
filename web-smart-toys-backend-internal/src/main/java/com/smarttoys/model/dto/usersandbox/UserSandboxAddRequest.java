package com.smarttoys.model.dto.usersandbox;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSandboxAddRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;


    /**
     * 沙盒名称
     */
    private String sandboxName;

    /**
     * 沙盒类型
     */
    private String sandboxType;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 沙盒 id
     */
    private Long sandboxId;


}
