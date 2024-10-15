package com.yc.service;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/admin/{uid}")  // 在 WebSocket 端点中通过路径参数传递 uid
@Component
public class AdminWebSocketServer {
    // 使用 ConcurrentHashMap 来存储每个用户的 session，key 为 uid
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立时，获取 UID 并将其 Session 存储到 map 中
     */
    @OnOpen
    public void onOpen(@PathParam("uid") String uid, Session session) {
        sessionMap.put(uid, session);  // 将 uid 作为 key，存储用户的 session
        System.out.println("WebSocket 连接已经建立，用户 UID：" + uid);
    }

    /**
     * 收到消息时的处理
     */
    @OnMessage
    public void onMessage(String message, @PathParam("uid") String uid, Session session) throws IOException {
        System.out.println("收到客户端消息，用户 UID：" + uid + "，消息：" + message);
        // 可以根据业务逻辑对收到的消息进行处理，如通知前端进行刷新
    }

    /**
     * 关闭连接时，移除该用户的 session
     */
    @OnClose
    public void onClose(@PathParam("uid") String uid) {
        sessionMap.remove(uid);  // 移除 session
        System.out.println("WebSocket 连接已经关闭，用户 UID：" + uid);
    }

    /**
     * 发生错误时的处理
     */
    @OnError
    public void onError(@PathParam("uid") String uid, Throwable t) {
        System.out.println("WebSocket 连接出现错误，用户 UID：" + uid + "，错误信息：" + t.getMessage());
    }

    /**
     * 发送消息给指定用户，告诉前端需要刷新
     */
    public void sendToUser(String uid, String message) throws IOException {
        Session session = sessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 群发消息，发送给所有连接的用户
     */
    public void broadcast(String message) throws IOException {
        for (Session session : sessionMap.values()) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
}
