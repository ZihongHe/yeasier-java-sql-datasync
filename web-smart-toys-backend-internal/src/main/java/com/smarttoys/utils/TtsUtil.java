package com.smarttoys.utils;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 语音合成工具类
 * 用于将文本合成为语音并返回Base64编码的音频数据
 */
public class TtsUtil {

    // json解析器
    private static final Gson gson = new Gson();

    // 默认配置
    private static final String HOST_URL = "https://tts-api.xfyun.cn/v2/tts";
    private static final String DEFAULT_VCN = "x4_yezi"; // 默认发音人
    private static final String DEFAULT_TTE = "UTF8"; // 默认文本编码

    /**
     * 语音合成主方法 - 返回Base64编码的音频数据
     * @param appId 讯飞开放平台应用ID
     * @param apiKey 讯飞开放平台API Key
     * @param apiSecret 讯飞开放平台API Secret
     * @param text 要合成的文本
     * @return Base64编码的完整音频数据（包含WAV头）
     * @throws Exception 合成过程中发生的异常
     */
    public static String synthesize(String appId, String apiKey, String apiSecret, String text) throws Exception {
        return synthesize(appId, apiKey, apiSecret, text, DEFAULT_VCN, DEFAULT_TTE);
    }

    /**
     * 语音合成主方法（带自定义发音人和编码）- 返回Base64编码的音频数据
     * @param appId 讯飞开放平台应用ID
     * @param apiKey 讯飞开放平台API Key
     * @param apiSecret 讯飞开放平台API Secret
     * @param text 要合成的文本
     * @param vcn 发音人参数
     * @param tte 文本编码格式
     * @return Base64编码的完整音频数据（包含WAV头）
     * @throws Exception 合成过程中发生的异常
     */
    public static String synthesize(String appId, String apiKey, String apiSecret, String text, String vcn, String tte) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("合成文本不能为空");
        }

        // 获取鉴权后的WebSocket URL
        String wsUrl = getAuthUrl(HOST_URL, apiKey, apiSecret).replace("https://", "wss://");

        // 在内存中收集音频数据
        ByteArrayOutputStream audioDataBuffer = new ByteArrayOutputStream();
        // 先预留44字节用于WAV头
        byte[] wavHeaderPlaceholder = new byte[44];
        audioDataBuffer.write(wavHeaderPlaceholder);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean success = new AtomicBoolean(false);
        AtomicReference<String> errorMessage = new AtomicReference<>();
        final int[] totalAudioLength = {0}; // 用于记录实际音频数据长度

        try {
            URI uri = new URI(wsUrl);
            WebSocketClient webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String message) {
                    JsonParse myJsonParse = gson.fromJson(message, JsonParse.class);
                    if (myJsonParse.code != 0) {
                        String errorMsg = "合成错误，错误码: " + myJsonParse.code + ", SID: " + myJsonParse.sid;
                        System.err.println(errorMsg);
                        errorMessage.set(errorMsg);
                        latch.countDown();
                        return;
                    }

                    if (myJsonParse.data != null && myJsonParse.data.audio != null) {
                        try {
                            byte[] audioBytes = Base64.getDecoder().decode(myJsonParse.data.audio);
                            audioDataBuffer.write(audioBytes);
                            totalAudioLength[0] += audioBytes.length;

                            if (myJsonParse.data.status == 2) {
                                // 合成完成，写入正确的WAV头
                                try {
                                    byte[] wavHeader = generateWavHeader(totalAudioLength[0], 16000, 1, 16);
                                    // 将WAV头写入到缓冲区的开头
                                    byte[] completeAudio = audioDataBuffer.toByteArray();
                                    System.arraycopy(wavHeader, 0, completeAudio, 0, 44);

                                    // 将完整的音频数据转换为Base64
                                    String base64Audio = Base64.getEncoder().encodeToString(completeAudio);

                                    success.set(true);
                                    // 存储结果到AtomicReference或其他方式，这里简化处理
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    errorMessage.set("写入WAV头失败: " + e.getMessage());
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            errorMessage.set("音频数据处理失败: " + e.getMessage());
                        }
                    }

                    if (myJsonParse.data != null && myJsonParse.data.status == 2) {
                        latch.countDown();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                    latch.countDown();
                }

                @Override
                public void onError(Exception e) {
                    String errorMsg = "WebSocket错误: " + e.getMessage();
                    System.err.println(errorMsg);
                    e.printStackTrace();
                    errorMessage.set(errorMsg);
                    latch.countDown();
                }
            };

            // 建立连接
            webSocketClient.connect();

            // 等待连接建立（5秒超时）
            int timeout = 50;
            while (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN) && timeout > 0) {
                Thread.sleep(100);
                timeout--;
            }

            if (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                throw new RuntimeException("WebSocket连接超时");
            }

            // 发送请求
            String requestJson = createRequestJson(appId, vcn, tte, text);
            webSocketClient.send(requestJson);

            // 等待合成完成或超时（30秒）
            if (!latch.await(30, TimeUnit.SECONDS)) {
                webSocketClient.close();
                throw new RuntimeException("语音合成超时");
            }

            webSocketClient.close();

            if (!success.get()) {
                throw new RuntimeException(errorMessage.get() != null ? errorMessage.get() : "语音合成失败，未知错误");
            }

            // 获取完整的音频数据并转换为Base64
            byte[] completeAudio = audioDataBuffer.toByteArray();
            byte[] wavHeader = generateWavHeader(totalAudioLength[0], 16000, 1, 16);
            System.arraycopy(wavHeader, 0, completeAudio, 0, 44);

            return Base64.getEncoder().encodeToString(completeAudio);

        } catch (Exception e) {
            System.err.println("语音合成异常: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            try {
                audioDataBuffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建请求JSON
     */
    private static String createRequestJson(String appId, String vcn, String tte, String text) {
        return "{\n" +
                "  \"common\": {\n" +
                "    \"app_id\": \"" + appId + "\"\n" +
                "  },\n" +
                "  \"business\": {\n" +
                "    \"aue\": \"raw\",\n" +
                "    \"tte\": \"" + tte + "\",\n" +
                "    \"vcn\": \"" + vcn + "\",\n" +
                "    \"pitch\": 50,\n" +
                "    \"speed\": 50\n" +
                "  },\n" +
                "  \"data\": {\n" +
                "    \"status\": 2,\n" +
                "    \"text\": \"" + Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8)) + "\"\n" +
                "  }\n" +
                "}";
    }

    /**
     * 获取鉴权URL
     */
    private static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";

        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        String sha = Base64.getEncoder().encodeToString(hexDigits);

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                apiKey, "hmac-sha256", "host date request-line", sha);

        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();

        return httpUrl.toString();
    }

    /**
     * 生成WAV文件头
     */
    private static byte[] generateWavHeader(int audioLength, int sampleRate, int channels, int bitsPerSample) {
        int byteRate = sampleRate * channels * bitsPerSample / 8;
        int blockAlign = channels * bitsPerSample / 8;

        ByteArrayOutputStream header = new ByteArrayOutputStream();
        try {
            header.write("RIFF".getBytes()); // RIFF header
            header.write(intToByteArray(36 + audioLength)); // 文件大小
            header.write("WAVE".getBytes()); // WAVE tag
            header.write("fmt ".getBytes()); // fmt chunk
            header.write(intToByteArray(16)); // fmt chunk size
            header.write(shortToByteArray((short) 1)); // 音频格式 (1 = PCM)
            header.write(shortToByteArray((short) channels)); // 声道数
            header.write(intToByteArray(sampleRate)); // 采样率
            header.write(intToByteArray(byteRate)); // 字节率
            header.write(shortToByteArray((short) blockAlign)); // 块对齐
            header.write(shortToByteArray((short) bitsPerSample)); // 位深度
            header.write("data".getBytes()); // data chunk
            header.write(intToByteArray(audioLength)); // 音频数据大小
        } catch (IOException e) {
            e.printStackTrace();
        }
        return header.toByteArray();
    }

    /**
     * 整数转字节数组 (小端序)
     */
    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff),
                (byte) ((value >> 16) & 0xff),
                (byte) ((value >> 24) & 0xff)
        };
    }

    /**
     * 短整型转字节数组 (小端序)
     */
    private static byte[] shortToByteArray(short value) {
        return new byte[] {
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff)
        };
    }

    /**
     * JSON解析类
     */
    static class JsonParse {
        int code;
        String message;
        String sid;
        Data data;
    }

    /**
     * 数据类
     */
    static class Data {
        int status;
        String audio;
        String ced;
    }
}