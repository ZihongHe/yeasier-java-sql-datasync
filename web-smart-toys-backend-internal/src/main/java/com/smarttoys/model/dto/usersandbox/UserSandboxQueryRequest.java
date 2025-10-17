package com.smarttoys.model.dto.usersandbox;

import com.smarttoys.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSandboxQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long sandboxId;

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
     * 创建时间
     */
    private Date createTime;



    /**
     * 是否删除
     */
    private Integer isDelete;
}
