package com.smarttoys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.constant.CommonConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.agent.AgentQueryRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.vo.AgentVO;
import com.smarttoys.model.vo.UserVO;
import com.smarttoys.service.AgentService;
import com.smarttoys.mapper.AgentMapper;
import com.smarttoys.service.UserService;
import com.smarttoys.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 明月
* @description 针对表【agent(智能体)】的数据库操作Service实现
* @createDate 2025-09-25 17:30:48
*/
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent>
    implements AgentService{

    @Resource
    private UserService userService;



    /**
     * 获取查询包装类
     *
     * @param agentQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Agent> getQueryWrapper(AgentQueryRequest agentQueryRequest) {
        QueryWrapper<Agent> queryWrapper = new QueryWrapper<>();
        if (agentQueryRequest == null) {
            return queryWrapper;
        }

        String sortField = agentQueryRequest.getSortField();
        String sortOrder = agentQueryRequest.getSortOrder();
        Long agentId = agentQueryRequest.getAgentId();
        String agentName = agentQueryRequest.getAgentName();
        Long userId = agentQueryRequest.getUserId();
        Long sandboxId = agentQueryRequest.getSandboxId();
        Integer onlineStatus = agentQueryRequest.getOnlineStatus();
        Integer isDelete = agentQueryRequest.getIsDelete();
        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(agentId), "id", agentId);
        queryWrapper.like(StringUtils.isNotBlank(agentName), "agentName", agentName);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(sandboxId), "sandboxId", sandboxId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(onlineStatus), "onlineStatus", onlineStatus);
        queryWrapper.eq(ObjectUtils.isNotEmpty(isDelete), "isDelete", isDelete);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public AgentVO getAgentVO(Agent agent, HttpServletRequest request) {
        AgentVO agentVO = AgentVO.objToVo(agent);
        long agentId = agent.getAgentId();
        // 1. 关联查询用户信息
        Long userId = agent.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        agentVO.setUser(userVO);
        return agentVO;
    }

    @Override
    public Page<AgentVO> getAgentVOPage(Page<Agent> agentPage, HttpServletRequest request) {
        List<Agent> agentList = agentPage.getRecords();
        Page<AgentVO> agentVOPage = new Page<>(agentPage.getCurrent(), agentPage.getSize(), agentPage.getTotal());
        if (CollUtil.isEmpty(agentList)) {
            return agentVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = agentList.stream().map(Agent::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getUserId));

        // 填充信息
        List<AgentVO> agentVOList = agentList.stream().map(agent -> {
            AgentVO agentVO = AgentVO.objToVo(agent);
            Long userId = agent.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            agentVO.setUser(userService.getUserVO(user));

            return agentVO;
        }).collect(Collectors.toList());
        agentVOPage.setRecords(agentVOList);
        return agentVOPage;
    }

}




