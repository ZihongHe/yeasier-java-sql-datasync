package com.smarttoys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.exception.ThrowUtils;
import com.smarttoys.model.dto.user.UserAddRequest;
import com.smarttoys.model.dto.user.UserQueryRequest;
import com.smarttoys.model.dto.user.UserUpdateBalanceRequest;
import com.smarttoys.model.dto.user.UserUpdateRequest;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.vo.UserVO;
import com.smarttoys.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
     * 查询用户
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

    // region 登录相关

    /**
     * 新增用户
     *
     * @param userAddRequest
     * @return
     */
    @PostMapping("/add_user")
    public BaseResponse<String> userRegister(@RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String result = userService.userRegister(userAddRequest);
        return ResultUtils.success(result);
    }


    /**
     * 更新用户
     */
    @PostMapping("/update_user")
    public BaseResponse<UserVO> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        UserVO userVO = userService.updateUserByAccountPassword(userUpdateRequest);
        return ResultUtils.success(userVO);
    }

    /**
     * 更新配额
     */
    @PostMapping("/update_balance")
    public BaseResponse<BigDecimal> updateBalance(@RequestBody UserUpdateBalanceRequest userUpdateBalanceRequest) {
        if (userUpdateBalanceRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userUpdateBalanceRequest.getUserAccount());
        User user = userService.getOne(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.USER_NOT_EXIST, "用户不存在");
        BigDecimal balanceQuota = user.getUserBalance().add(userUpdateBalanceRequest.getBalanceQuota());
        user.setUserBalance(balanceQuota);
        userService.updateById(user);
        return ResultUtils.success(balanceQuota);
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


}
