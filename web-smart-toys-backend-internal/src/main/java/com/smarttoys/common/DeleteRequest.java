package com.smarttoys.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}