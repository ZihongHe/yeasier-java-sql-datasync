package com.smarttoys.service.impl;

import com.smarttoys.service.VerificationcodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationcodeServiceIml implements VerificationcodeService {
    @Override
    public Integer checkVerificationcode(String verificationcode) {
        System.out.println("验证码" + verificationcode);
        if (verificationcode == null){
            return 0;
        }
        // 普通权限
        if(verificationcode.equals("helloworld111")){
            return 1;
        }
        // 高级权限
        else if(verificationcode.equals("helloworld123")|| verificationcode.equals("lianxiang2025yeasier")){
            return 2;
        }
        return 0;
    }

    @Override
    public Integer checkAnty(String verificationcode) {
        return null;
    }

    @Override
    public Integer checkBarr(String verificationcode) {
        return null;
    }

    @Override
    public Integer checkCathy(String verificationcode) {
        return null;
    }

    @Override
    public Integer checkOwen(String verificationcode) {
        return null;
    }

    @Override
    public Integer checkJunie(String verificationcode) {
        return null;
    }
}
