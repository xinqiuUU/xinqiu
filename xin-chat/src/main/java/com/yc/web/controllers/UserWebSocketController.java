package com.yc.web.controllers;

import com.yc.bean.WebSocket;
import com.yc.bean.model.JsonModel;
import com.yc.service.UserWebSocketServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户的 WebSocket 控制器
 */
@RestController
@RequestMapping("/chat/user")
public class UserWebSocketController {

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    private UserWebSocketServer userWebSocketServer;
    @Autowired
    public void setUserWebSocketServer(UserWebSocketServer userWebSocketServer) {
        this.userWebSocketServer = userWebSocketServer;
    }

    // 模拟数据库，存储用户的 WebSocket 连接
    @PostMapping(value = "/getServerInfo")
    public JsonModel getServerInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> serverInfo = new HashMap<>();
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();

        String protocol = request.isSecure() ? "wss" : "ws";
        String host = "localhost";
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        serverInfo.put("protocol", protocol);
        serverInfo.put("host", host);
        serverInfo.put("port", String.valueOf(port));
        serverInfo.put("contextPath", contextPath);
        serverInfo.put("uid",uid);

        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(serverInfo);
        return jm; // Spring 会自动将这个对象转换为 JSON
    }

    // 获取  服务端  发 的   消息
    @PostMapping(value = "/getMessage")
    public JsonModel getMessage(HttpServletRequest req) {
        JsonModel jm = new JsonModel();
        Map<String, Object> userClaims = (Map<String, Object>) req.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();

        // 使用 RedisTemplate 获取有序集合的全部成员
        Set<DefaultTypedTuple<String>> sortedSetMembers = redisTemplate.opsForZSet().rangeWithScores("content_" + uid, 0, -1);
        List<WebSocket> list = new ArrayList<>();

        // 打印输出每个成员及其分数
        for (DefaultTypedTuple<String> member : sortedSetMembers) {
            String memberValue = member.getValue(); // 内容
            Double score = member.getScore();        // 时间戳
            String[] parts = memberValue.split(":");

            // 检查 parts 数组的长度
            if (parts.length < 2) {
                continue;
            }

            WebSocket webSocket = new WebSocket();
            webSocket.setName(parts[0]);
            webSocket.setContent(parts[1]);

            Date date = new Date(score.longValue() * 1000); // 将 score 转换为 long
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(date);
            webSocket.setTimestamp(timestamp);
            list.add(webSocket);
        }

        jm.setCode(1);
        jm.setObj(list);
        return jm;
    }

    // 发送信息给服务端
    @PostMapping(value = "/setMessage")
    public JsonModel setMessage(HttpServletRequest req, @RequestParam("content") String content) throws IOException {
        JsonModel jm = new JsonModel();
        // 拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) req.getAttribute("userClaims");
        String uid = userClaims.get("uid").toString();

        // 当前时间戳
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // 使用 RedisTemplate 添加新成员到有序集合
        redisTemplate.opsForZSet().add("content_" + uid, userClaims.get("uname") + ":" + content, currentTimestamp);

        jm.setCode(1);
        jm.setObj("发送成功");

        // 发送消息给 WebSocket 服务器
        userWebSocketServer.send(uid);

        return jm;
    }

}
