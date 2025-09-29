package com.smarttoys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 沙盒
 * @TableName sandbox
 */
@TableName(value ="sandbox")
@Data
public class Sandbox {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long sandboxId;

    /**
     * 沙盒类型
     */
    private String sandboxType;

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
}