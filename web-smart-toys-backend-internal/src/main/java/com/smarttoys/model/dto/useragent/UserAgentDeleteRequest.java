package com.smarttoys.model.dto.useragent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserAgentDeleteRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * id
     */
    private Long userAgentId;

}
