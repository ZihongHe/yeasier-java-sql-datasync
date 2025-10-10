package com.smarttoys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smarttoys.annotation.AuthCheck;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.DeleteRequest;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.constant.UserConstant;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.user.UserLoginRequest;
import com.smarttoys.model.dto.user.UserQueryRequest;
import com.smarttoys.model.dto.user.UserRegisterRequest;
import com.smarttoys.model.dto.user.UserUpdateMyRequest;
import com.smarttoys.model.dto.user.UserUpdateRequest;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.vo.LoginUserVO;
import com.smarttoys.model.vo.UserVO;
import com.smarttoys.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.smarttoys.service.impl.UserServiceImpl.SALT;

/**
 * 用户接口
 *
 
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 查询用户，登录
     */
    @PostMapping("/query_user")
    public BaseResponse<UserVO> queryUser(@RequestBody UserQueryRequest userQueryRequest, HttpServletRequest request) {
        // 校验参数
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String userAccount = userQueryRequest.getUserAccount();
        String userPassword = userQueryRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        // 查询用户
        UserVO userVO = userService.queryUser(userQueryRequest, request);
        return ResultUtils.success(userVO);
    }
    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * 查询配额
     */
    @PostMapping("/query_balance")
    public BaseResponse<BigDecimal> queryBalance(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userQueryRequest.getUserAccount());
        User user = userService.getOne(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.USER_NOT_EXIST, "用户不存在");
        return ResultUtils.success(user.getUserBalance());
    }

    /**
     * 获取当前登陆用户全部智能体的名字
     *
     */
    @PostMapping("/get_agent")
    public BaseResponse<List<String>> getAgent(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        List<String> agentNames = userService.getAgentNames(user.getUserId());
        return ResultUtils.success(agentNames);
    }
    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

}
