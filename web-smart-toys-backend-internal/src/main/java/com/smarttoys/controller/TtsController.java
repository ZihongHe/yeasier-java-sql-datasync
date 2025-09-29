package com.smarttoys.controller;

import com.smarttoys.utils.TtsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// TtsController.java
@RestController
@RequestMapping("/tts")
@Slf4j
public class TtsController {

    @Value("a0ead230")
    private String appId;

    @Value("e2889af669c621344e0dc709081c8fe5")
    private String apiKey;

    @Value("MzFjNjE5NmExZjI0MDJhOTJjODNiYmEx")
    private String apiSecret;


    @PostMapping("/speak")
    public ResponseEntity<String> synthesize(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("文本不能为空");
        }

        try {
            String filePath = TtsUtil.synthesize(appId, apiKey, apiSecret, text);
            return ResponseEntity.ok(filePath); // 返回文件路径
        } catch (Exception e) {
            log.error("合成失败：{}", e.getMessage(), e);
            return ResponseEntity.status(500).body("合成失败：" + e.getMessage());
        }
    }
}