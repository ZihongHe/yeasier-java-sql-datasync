package com.smarttoys.model.dto.usersandbox;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserSandboxUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long userSandboxId;

    /**
     * 沙盒名称
     */
    private String sandboxName;

    /**
     * 沙盒类型
     */
    private String sandboxType;

    /**
     * 所属用户 id
     */
    private Long userId;

    /**
     * 沙盒 id
     */
    private Long sandboxId;

    /**
     * 是否删除
     */
    private Integer isDelete;
}
