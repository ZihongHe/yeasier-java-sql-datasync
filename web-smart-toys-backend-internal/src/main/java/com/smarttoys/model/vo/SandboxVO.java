package com.smarttoys.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smarttoys.model.entity.Sandbox;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SandboxVO implements Serializable {
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
     * @param sandboxVO
     * @return
     */
    public static Sandbox voToObj(SandboxVO sandboxVO) {
        if (sandboxVO == null) {
            return null;
        }
        Sandbox sandbox = new Sandbox();
        BeanUtils.copyProperties(sandboxVO, sandbox);
        return sandbox;
    }

    /**
     * 对象转包装类
     *
     * @param sandbox
     * @return
     */
    public static SandboxVO objToVo(Sandbox sandbox) {
        if (sandbox == null) {
            return null;
        }
        SandboxVO sandboxVO = new SandboxVO();
        BeanUtils.copyProperties(sandbox, sandboxVO);

        return sandboxVO;
    }

    /**
     * 列表转包装类
     *
     * @param sandboxList
     * @return
     */
    public static List<SandboxVO> objListToVoList(List<Sandbox> sandboxList) {
        List<SandboxVO> sandboxVOList = new ArrayList<>() ;
        for (Sandbox sandbox : sandboxList) {
            sandboxVOList.add(objToVo(sandbox));
        }
        return sandboxVOList;
    }
}
