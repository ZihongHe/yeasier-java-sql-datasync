package com.smarttoys.model.dto.usersandbox;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSandboxDeleteRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * id
     */
    private Long userSandboxId;

}
