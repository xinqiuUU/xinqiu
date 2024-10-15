package com.yc.web.controllers;

import com.yc.bean.model.JsonModel;
import com.yc.service.AdminWebSocketServer;
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
 * 管理员的 WebSocket 控制器
 */
@RestController
@RequestMapping("/chat/admin")
public class AdminWebSocketController {

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // 模拟数据库，存储用户的 WebSocket 连接
    @PostMapping(value = "/getServerInfo")
    public JsonModel getServerInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> serverInfo = new HashMap<>();
        String protocol = request.isSecure() ? "wss" : "ws";
        String host = "localhost";
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        serverInfo.put("protocol", protocol);
        serverInfo.put("host", host);
        serverInfo.put("port", String.valueOf(port));
        serverInfo.put("contextPath", contextPath);


        JsonModel jm = new JsonModel();
        jm.setCode(1);
        jm.setObj(serverInfo);
        return jm; // Spring 会自动将这个对象转换为 JSON
    }

    @Autowired
    private AdminWebSocketServer adminWebSocketServer;

    /**
     * 获取通知后，刷新数据
     */
    @PostMapping(value = "/responseNotice")
    public JsonModel responseNotice(@RequestParam("uid") String uid) {
        JsonModel jm = new JsonModel();

        // 回传给前端的聊天内容
        List<Map<String, Object>> returnList = new ArrayList<>();

        // 获取有序集合的全部成员
        Set<DefaultTypedTuple<String>> sortedSetMembers = redisTemplate.opsForZSet().rangeWithScores("content_" + uid, 0, -1);

        // 打印输出每个成员及其分数
        for (DefaultTypedTuple<String> member : sortedSetMembers) {
            String memberValue = member.getValue(); // 内容
            String timeForDat = getDateFormat(member.getScore().longValue());

            Map<String, Object> map = new HashMap<>();
            map.put("time", timeForDat);
            map.put("content", memberValue);
            returnList.add(map);
        }

        jm.setCode(1);
        jm.setObj(returnList);
        return jm;
    }


    // 发送消息给指定用户
    @PostMapping(value = "/setMassage")
    public JsonModel setMassage(HttpServletRequest req, @RequestParam("content") String content, @RequestParam("uid") String uid) throws IOException {
        JsonModel jm = new JsonModel();

        System.out.println("当前发送对象：" + uid);
        System.out.println("当前发送内容：" + content);

        long currentTimestamp = System.currentTimeMillis() / 1000;

        // 向数据库添加当前用户信息
        redisTemplate.opsForZSet().add("content_" + uid, "admin:" + content, currentTimestamp);

        // 发通知给客户
//        adminWebSocketServer.send("admin");
        adminWebSocketServer.sendToUser(uid,"admin");

        // 回传给前端的聊天内容
        List<Map<String, Object>> returnList = new ArrayList<>();

        // 获取有序集合的全部成员
        Set<DefaultTypedTuple<String>> sortedSetMembers = redisTemplate.opsForZSet().rangeWithScores("content_" + uid, 0, -1);

        // 打印输出每个成员及其分数
        for (DefaultTypedTuple<String> member : sortedSetMembers) {
            String memberValue = member.getValue(); // 内容
            String timeForDat = getDateFormat(member.getScore().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("time", timeForDat);
            map.put("content", memberValue);
            returnList.add(map);
        }

        // 获取更新的数据，回传给前端
        jm.setCode(1);
        jm.setObj(returnList);
        return jm;
    }


    /**
     * 初始化聊天数据
     */
    @PostMapping(value = "/initChatContent")
    public JsonModel initChatContent() {
        JsonModel jm = new JsonModel();

        Set<String> keys = redisTemplate.keys("content_*");

        // 回传的用户ID列表
        List<Integer> users = new ArrayList<>();
        // 每个用户对应的消息内容
        List<List<Map<String, Object>>> messages = new ArrayList<>();

        // 循环每一个集合
        for (String key : keys) {
            // 获取当前用户的消息集合
            Set<DefaultTypedTuple<String>> sortedSetMembers = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
            System.out.println("当前集合为---" + key); // content_2

            // 获取 ID 存到用户列表
            String[] parts = key.split("_");
            int userId = Integer.parseInt(parts[1]);
            users.add(userId);

            List<Map<String, Object>> list = new ArrayList<>();
            // 获取每个集合的内容
            for (DefaultTypedTuple<String> member : sortedSetMembers) {
                String chatContent = member.getValue(); // 内容
                String timeFormat = getDateFormat(member.getScore().longValue()); // 将时间戳转换为日期格式的时间
                System.out.println(chatContent);
                System.out.println(timeFormat);

                Map<String, Object> map = new HashMap<>();
                map.put("time", timeFormat);
                map.put("content", chatContent);
                list.add(map);
            }
            messages.add(list);
        }

        System.out.println(users);
        System.out.println(messages);

        List<Object> resultList = new ArrayList<>();
        resultList.add(users);
        resultList.add(messages);

        jm.setCode(1);
        jm.setObj(resultList);
        return jm;
    }


    /**
     * 将时间撮转换为日期格式事件
     * @param score
     * @return
     */
    public static String getDateFormat(Long score){
        // 创建 Date 对象并设置时间戳（需要乘以1000转换为毫秒）
        Date date = new Date(score * 1000L);

        // 创建 SimpleDateFormat 对象，定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 使用 SimpleDateFormat 格式化 Date 对象
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
