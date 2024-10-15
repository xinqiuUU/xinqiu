package com.yc.service;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ServerEndpoint("/ws")
@Component
public class WebSocketServer {
    private static Session session;
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket 连接已经建立。");
        WebSocketServer.session = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到客户端消息：" + message);
        //session.getBasicRemote().sendText("服务器收到消息：" + message);
    }

    @OnClose
    public void onClose() {
        System.out.println("WebSocket 连接已经关闭。");
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("WebSocket 连接出现错误：" + t.getMessage());
    }
    // ******  发送 通知  告诉前端需要刷新了 即前端需要去redis数据库中读取消息了
    public void send(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
