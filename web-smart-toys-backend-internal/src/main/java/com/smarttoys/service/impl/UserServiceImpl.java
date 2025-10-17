package com.smarttoys.service.impl;

import static com.smarttoys.constant.UserConstant.USER_LOGIN_STATE;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.exception.BusinessException;
import com.smarttoys.mapper.AgentMapper;
import com.smarttoys.mapper.UserMapper;
import com.smarttoys.model.dto.user.UserAddRequest;
import com.smarttoys.model.dto.user.UserQueryRequest;
import com.smarttoys.model.dto.user.UserUpdateRequest;
import com.smarttoys.model.entity.Agent;
import com.smarttoys.model.entity.User;
import com.smarttoys.model.enums.UserRoleEnum;
import com.smarttoys.model.vo.AgentVO;
import com.smarttoys.model.vo.LoginUserVO;
import com.smarttoys.model.vo.UserVO;
import com.smarttoys.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author 明月
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-09-25 16:45:54
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "yeasier";

    @Resource
    private UserMapper userMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public String userRegister(UserAddRequest userAddRequest) {
        String userAccount = userAddRequest.getUserAccount();
        String userPassword = userAddRequest.getUserPassword();
        String checkPassword = userAddRequest.getCheckPassword();
        String userEmail = userAddRequest.getUserEmail();
        BigDecimal userBalance = userAddRequest.getUserBalance();
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userEmail)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账号不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.USER_ALREADY_EXIST, "账号重复");
            }
            // 邮箱不能重复
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userEmail", userEmail);
            count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.USER_ALREADY_EXIST, "邮箱重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserEmail(userEmail);
            user.setUserBalance(userBalance);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getUserAccount();
        }
    }

    @Override
    public UserVO queryUser(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        // 校验
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        if (StringUtils.isAnyBlank(userQueryRequest.getUserAccount(), userQueryRequest.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userQueryRequest.getUserPassword()).getBytes());
        // 查询用户是否存在
        boolean isUserExist = false;

        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userAccount", userQueryRequest.getUserAccount());
        queryWrapper1.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper1);
        if (user == null) {
            QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("userEmail", userQueryRequest.getUserAccount());
            queryWrapper2.eq("userPassword", encryptPassword);
            user = this.baseMapper.selectOne(queryWrapper2);
        }

        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST, "用户或密码错误");
        }
        UserVO userVO = UserVO.objToVo(user);

        // TODO 关联用户和智能体、沙盒等信息
//        QueryWrapper<Agent> queryWrapperAgent = new QueryWrapper<>();
//        queryWrapperAgent.eq("userId", user.getUserId());
//        List<Agent> agentList = agentMapper.selectList(queryWrapperAgent);
//        List<AgentVO> agentVOList = AgentVO.objListToVoList(agentList);
//        userVO.setAgents(agentVOList);


//        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // TODO 保存登陆状态
        return userVO;
    }



    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 在调用 getLoginUser 之前添加调试代码
        Object sessionUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        System.out.println("Session中的用户对象类型: " + (sessionUser != null ? sessionUser.getClass() : "null"));
        System.out.println("Session中的用户对象值: " + sessionUser);

        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);

        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            System.out.println("当前未登录");
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getUserId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            System.out.println("用户不存在");
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getUserId();
        return this.getById(userId);
    }



    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long userId = userQueryRequest.getUserId();
        String userAccount = userQueryRequest.getUserAccount();
        String userPassword = userQueryRequest.getUserPassword();
        String userName = userQueryRequest.getUserName();
        String userRole = userQueryRequest.getUserRole();
        String userEmail = userQueryRequest.getUserEmail();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userAccount != null , "userAccount", userAccount);
        queryWrapper.eq(userPassword != null, "userPassword", userPassword);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.eq(userName != null, "userName", userName);
        queryWrapper.eq(userRole != null, "userRole", userRole);
        queryWrapper.eq(userEmail != null, "userEmail", userEmail);
        return queryWrapper;
    }

    @Override
    public UserVO updateUserByAccountPassword(UserUpdateRequest userUpdateRequest) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userUpdateRequest.getUserPassword()).getBytes());
        String userAccount = userUpdateRequest.getUserAccount();
        if (StringUtils.isAnyBlank(userAccount, encryptPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 创建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        // 校验用户是否存在
        User oldUser = this.baseMapper.selectOne(queryWrapper);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST, "用户不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        // 更新用户信息
        user.setUserId(oldUser.getUserId());
        user.setUserPassword(encryptPassword);
        boolean result = this.updateById(user);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新用户失败");
        }
        return this.getUserVO(this.getById(user.getUserId()));
    }
}
