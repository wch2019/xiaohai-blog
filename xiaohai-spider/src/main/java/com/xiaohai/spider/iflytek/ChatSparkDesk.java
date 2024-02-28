package com.xiaohai.spider.iflytek;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaohai.spider.iflytek.dto.MsgDTO;
import com.xiaohai.spider.iflytek.dto.RequestDTO;
import com.xiaohai.spider.iflytek.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Payload;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 星火流式请求客户端
 *
 * @author wangchenghai
 * @date 2023/08/21 16:00:27
 */
@Slf4j
public class ChatSparkDesk extends WebSocketListener {
    private static final String API_HOST = "spark-api.xf-yun.com";
    private static final String API_PATH = "/v1.1/chat";
    private static final String APP_ID = "";
    private static final String API_KEY = "";
    private static final String API_SECRET = "";


    /**
     * 获取验证请求url
     *
     * @return
     */
    public static String getAuthorizationUrl() {
        try {
            // 获取鉴权时间 date
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());

            // 获取signature_origin字段
            StringBuilder builder = new StringBuilder("host: ").append(API_HOST).append("\n").
                    append("date: ").append(date).append("\n").
                    append("GET ").append(API_PATH).append(" HTTP/1.1");

            // 获得signatue
            Charset charset = StandardCharsets.UTF_8;
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec sp = new SecretKeySpec(API_SECRET.getBytes(charset), "hmacsha256");
            mac.init(sp);
            byte[] basebefore = mac.doFinal(builder.toString().getBytes(charset));
            // Base64加密
            String signature = Base64.getEncoder().encodeToString(basebefore);
            //获得 authorization_origin
            String authorizationOrigin = String.format("api_key=\"%s\",algorithm=\"%s\",headers=\"%s\",signature=\"%s\"", API_KEY, "hmac-sha256", "host date request-line", signature);
            //获得authorization
            String authorization = Base64.getEncoder().encodeToString(authorizationOrigin.getBytes(charset));
            // 获取httpUrl
            Map<String, Object> param = new HashMap<>();
            param.put("authorization", authorization);
            param.put("date", date);
            param.put("host", API_HOST);

            String toParams = HttpUtil.toParams(param);

            return "wss://" + API_HOST + API_PATH + "?" + toParams;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取请求参数
     *
     * @param uid
     * @param msgList
     * @return
     */
    public static RequestDTO getRequestParam(String uid, List<MsgDTO> msgList) {
        RequestDTO dto = new RequestDTO();
        dto.setHeader(new RequestDTO.HeaderDTO(APP_ID, uid));
        dto.setParameter(new RequestDTO.ParameterDTO(new RequestDTO.ParameterDTO.ChatDTO()));
        dto.setPayload(new RequestDTO.PayloadDTO(new RequestDTO.PayloadDTO.MessageDTO(msgList)));
        return dto;
    }

    /**
     * 发送消息
     *
     * @param uid
     * @param msgList
     * @return
     */
    public static WebSocket sendMsg(String uid, List<MsgDTO> msgList, WebSocketListener listener) {
        // 获取鉴权url
        String authorizationUrl = getAuthorizationUrl();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(authorizationUrl).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, listener);
        RequestDTO requestDTO = getRequestParam(uid, msgList);
        log.info("param:{}",JSON.toJSONString(requestDTO));

        webSocket.send(JSON.toJSONString(requestDTO));
        return webSocket;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        log.info("WebSocket连接已打开");
        webSocket.send("Hello, WebSocket!");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        log.info("收到消息： " + text);
        ResponseDTO responseData = JSONObject.parseObject(text, ResponseDTO.class);
        if (0 == responseData.getHeader().getCode()) {
            ResponseDTO.PayloadDTO pl = responseData.getPayload();
            List<MsgDTO> tests = pl.getChoices().getText();
            MsgDTO textDTO = tests.stream().findFirst().orElse(new MsgDTO());
            log.info(textDTO.toString());
            if (2 == responseData.getHeader().getStatus()) {
                ResponseDTO.PayloadDTO.UsageDTO.TextDTO testDto = pl.getUsage().getText();
                Integer totalTokens = testDto.getTotalTokens();
                log.info("本次花费：" + totalTokens + " tokens");
                webSocket.close(3, "客户端主动断开链接");
            }
        } else {
            log.error("返回结果错误：\n" + responseData.getHeader().getCode() + responseData.getHeader().getMessage());
            webSocket.close(3, "客户端主动断开链接");
        }

    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        log.error("WebSocket连接已关闭");
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        log.error("WebSocket连接失败： " + t.getMessage());
    }

    public static void main(String[] args) {
        List<MsgDTO> list=new ArrayList<>();
        MsgDTO dto = MsgDTO.builder().role(MsgDTO.Role.USER.getName()).content("请介绍一下你自己").build();
        list.add(dto);
        WebSocket webSocket=sendMsg("123", list, new ChatSparkDesk());
    }
}
