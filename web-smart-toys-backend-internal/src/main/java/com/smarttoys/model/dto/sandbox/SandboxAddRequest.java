package com.smarttoys.model.dto.sandbox;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SandboxAddRequest implements Serializable {
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


}
