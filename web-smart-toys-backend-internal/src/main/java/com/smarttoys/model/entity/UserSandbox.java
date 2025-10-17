package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 沙盒
 * @TableName user_sandbox
 */
@TableName(value ="user_sandbox")
@Data
public class UserSandbox {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    @TableLogic
    private Integer isDelete;
}