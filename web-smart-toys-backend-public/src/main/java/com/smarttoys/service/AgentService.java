package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.model.dto.agent.AgentQueryRequest;
import com.smarttoys.model.entity.Agent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.vo.AgentVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 明月
* @description 针对表【agent(智能体)】的数据库操作Service
* @createDate 2025-09-25 17:30:48
*/
public interface AgentService extends IService<Agent> {




    /**
     * 获取查询条件
     *
     * @param agentQueryRequest
     * @return
     */
    QueryWrapper<Agent> getQueryWrapper(AgentQueryRequest agentQueryRequest);


    /**
     * 获取智能体封装
     *
     * @param agent
     * @param request
     * @return
     */
    AgentVO getAgentVO(Agent agent, HttpServletRequest request);

    /**
     * 分页获取智能体封装
     *
     * @param agentPage
     * @param request
     * @return
     */
    Page<AgentVO> getAgentVOPage(Page<Agent> agentPage, HttpServletRequest request);
}
