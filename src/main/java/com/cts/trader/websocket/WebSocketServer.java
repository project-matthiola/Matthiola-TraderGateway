package com.cts.trader.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    private static Queue<Session> sessionQueue = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(Session session) {
        sessionQueue.add(session);
        onlineCount.getAndIncrement();
        logger.info("新连接加入，当前在线人数为：" + onlineCount);
    }

    @OnClose
    public void onClose(Session session) {
        sessionQueue.remove(session);
        onlineCount.getAndDecrement();
        logger.info("连接关闭，当前在线人数：" + onlineCount);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessionQueue.remove(session);
        logger.error("发生错误");
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(final Session session, String message) {
        logger.info("来自客户端的消息：" + message);
        try {
            session.getBasicRemote().sendText("服务端已收到");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
