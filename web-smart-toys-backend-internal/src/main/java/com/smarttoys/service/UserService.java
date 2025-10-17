package com.smarttoys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smarttoys.model.dto.user.UserAddRequest;
import com.smarttoys.model.dto.user.UserQueryRequest;
import com.smarttoys.model.dto.user.UserUpdateRequest;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.vo.LoginUserVO;
import com.smarttoys.model.vo.UserVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


/**
 * @author 明月
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-09-25 16:45:54
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户
     */
    UserVO queryUser(UserQueryRequest userQueryRequest, HttpServletRequest request);


    /**
     * 用户注册
     *
     * @return 新用户 id
     */
    String userRegister(UserAddRequest userAddRequest);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);


    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);


    /**
     * 更新用户信息(根据账号和密码)
     *
     * @param userUpdateRequest
     * @return
     */
    UserVO updateUserByAccountPassword(UserUpdateRequest userUpdateRequest);
}
