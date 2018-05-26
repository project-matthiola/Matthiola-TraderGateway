package com.cts.trader.websocket;

import com.cts.trader.model.Broker;
import com.cts.trader.repository.BrokerRepository;
import com.cts.trader.service.OrderService;
import com.cts.trader.utils.HttpUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/mockServer")
@Component
public class MockServer {
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
    public void onMessage(final Session session, String message) throws Exception {
        logger.info("来自客户端的消息：" + message);
        mockMarket(session, message);

        //sendMessage(session, "reply");
    }

    private void mockMarket(Session session, String message) {
        int total = (int)(Math.random() * 50);
        JSONArray bidsList = new JSONArray();
        JSONArray asksList = new JSONArray();
        for (int i = 1;i <= total; i++) {
            List tmp = new ArrayList();
            tmp.add(i * 0.8);
            tmp.add(i * 100);
            asksList.add(tmp);
        }
        total = (int)(Math.random() * 50);
        for (int i = 1;i <= total; i++) {
            List tmp = new ArrayList();
            tmp.add((50 - i) * 0.8);
            tmp.add(i * 100);
            bidsList.add(tmp);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bids", bidsList);
        jsonObject.put("asks", asksList);
        JSONObject result = new JSONObject();
        result.put("brokerA,futures1", jsonObject);
        System.out.println(result);
        String res = result.toString();
        sendMessage(session, res);

    }

    private void sendMessage(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastAll(String msg) {
        for(Session s : sessionQueue) {
            sendMessage(s, msg);
        }
    }

}
