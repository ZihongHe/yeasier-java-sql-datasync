package com.smarttoys.controller;

import com.smarttoys.common.BaseResponse;
import com.smarttoys.common.ErrorCode;
import com.smarttoys.common.ResultUtils;
import com.smarttoys.service.VerificationcodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/code")
@Slf4j
public class VerificationcodeController {

    @Resource
    private VerificationcodeService verificationcodeService;

    /**
     * 验证验证码
     * @param verificationcode
     * @param session
     * @return
     */
    @PostMapping("/verify")
    public BaseResponse<Integer> verifyVerificationcode(@RequestParam("verificationcode") String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkAnty(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("serverAccess", true);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/Anty")
    public BaseResponse<Integer> verifyAnty(@RequestParam("verificationcode")String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkVerificationcode(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("AntyAccess", true);
        }
        return ResultUtils.success(result);
    }
    @PostMapping("/Barr")
    public BaseResponse<Integer> verifyBarr(@RequestParam("verificationcode")String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkVerificationcode(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("BarrAccess", true);
        }
        return ResultUtils.success(result);
    }
    @PostMapping("/Cathy")
    public BaseResponse<Integer> verifyCathy(@RequestParam("verificationcode")String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkVerificationcode(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("CathyAccess", true);
        }
        return ResultUtils.success(result);
    }
    @PostMapping("/Owen")
    public BaseResponse<Integer> verifyOwen(@RequestParam("verificationcode")String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkVerificationcode(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("OwenAccess", true);
        }
        return ResultUtils.success(result);
    }
    @PostMapping("/Junie")
    public BaseResponse<Integer> verifyJunie(@RequestParam("verificationcode")String verificationcode, HttpSession session) {
        Integer result = verificationcodeService.checkVerificationcode(verificationcode);
        if (result == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        // 验证成功时，在session中设置标志
        if (result == 1 || result == 2) {
            session.setAttribute("JunieAccess", true);
        }
        return ResultUtils.success(result);
    }

    // 检查访问权限的接口
    @GetMapping("/check-access")
    public BaseResponse<Boolean> checkAccess(HttpSession session) {
        Boolean hasAccess = (Boolean) session.getAttribute("serverAccess");
        return ResultUtils.success(hasAccess != null && hasAccess);
    }
}
