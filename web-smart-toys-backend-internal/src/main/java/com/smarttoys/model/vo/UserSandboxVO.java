package com.smarttoys.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.UserSandbox;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserSandboxVO implements Serializable {
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

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param userSandboxVO
     * @return
     */
    public static UserSandbox voToObj(UserSandboxVO userSandboxVO) {
        if (userSandboxVO == null) {
            return null;
        }
        UserSandbox userSandbox = new UserSandbox();
        BeanUtils.copyProperties(userSandboxVO, userSandbox);
        return userSandbox;
    }

    /**
     * 对象转包装类
     *
     * @param userSandbox
     * @return
     */
    public static UserSandboxVO objToVo(UserSandbox userSandbox) {
        if (userSandbox == null) {
            return null;
        }
        UserSandboxVO userSandboxVO = new UserSandboxVO();
        BeanUtils.copyProperties(userSandbox, userSandboxVO);

        return userSandboxVO;
    }

}
