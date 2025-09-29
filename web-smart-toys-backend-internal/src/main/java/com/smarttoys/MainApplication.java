package com.smarttoys;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * 主类（项目启动入口）
 *
 
 */
// todo 如需开启 Redis，须移除 exclude 中的内容
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.smarttoys.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {
//    @PostConstruct
//    public void initXunfei() {
//        try {
//            SpeechUtility.createUtility(SpeechConstant.APPID + "a0ead230");
//            System.out.println("科大讯飞 TTS 初始化成功");
//        } catch (Exception e) {
//            System.err.println("科大讯飞 TTS 初始化失败: " + e.getMessage());
//        }
//    }
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
