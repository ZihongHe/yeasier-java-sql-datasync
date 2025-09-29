package com.smarttoys.service;

public interface VerificationcodeService {


    Integer checkVerificationcode(String verificationcode);

    Integer checkAnty(String verificationcode);

    Integer checkBarr(String verificationcode);

    Integer checkCathy(String verificationcode);

    Integer checkOwen(String verificationcode);

    Integer checkJunie(String verificationcode);
}
