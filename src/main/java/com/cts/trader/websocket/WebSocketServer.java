package com.cts.trader.websocket;

import com.cts.trader.model.Broker;
import com.cts.trader.repository.BrokerRepository;
import com.cts.trader.service.OrderService;
import com.cts.trader.utils.HttpUtil;
import com.cts.trader.utils.SpringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description WebsocketServer
 * @version 1.0.0
 **/
@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {
    // 日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 在线人数统计
    private AtomicInteger onlineCount = new AtomicInteger(0);

    // 在线session记录
    private Queue<Session> sessionQueue = new ConcurrentLinkedQueue<>();

    // Redis连接
    private RedisTemplate redisTemplate;


    public Queue<Session> getSessionQueue() {
        return sessionQueue;
    }

    public AtomicInteger getOnlineCount() {
        return onlineCount;
    }

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

        String[] params = message.split(",");
        if (params[0].equals("orderBook")) {
            deliverFuturesMarket(session, message);
        } else if (params[0].equals("trade")) {
            deliverTradeHistory(session, message);
        }

    }

    private void deliverTradeHistory(Session session, String message) {
        if (!sessionQueue.contains(session)) {
            logger.info("当前在线人数：" + onlineCount);
            return;
        }
        String[] params = message.split(",");
        String futureID = params[1];
        String brokerName = params[2];

        String key = "trade," + brokerName + "," + futureID;
        System.out.println("key=" + key);
        String result = getTradeHistoryFromRedis(key);
        sendMessage(session, result);
        logger.info("服务端返回: " + result);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            onClose(session);
        }
    }

    private String getTradeHistoryFromRedis(String key) {
        redisTemplate = (RedisTemplate)SpringUtil.getBean("myRedisTemplate");

        if (!redisTemplate.hasKey(key)) return null;

        String trades = (String)redisTemplate.opsForValue().get(key);
        JSONArray jsonTrades = JSONArray.fromObject(trades);
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("type", "trade");
        jsonResult.put("data", jsonTrades);

        return jsonResult.toString();
    }

    private String getFuturesMarketFromRedis(String key) {
        redisTemplate = (RedisTemplate)SpringUtil.getBean("myRedisTemplate");

        if (!redisTemplate.hasKey(key)) return null;

        String market = (String)redisTemplate.opsForValue().get(key);
        JSONObject jsonMarket = JSONObject.fromObject(market);
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("type", "orderBook");
        jsonResult.put("data", jsonMarket);

        return jsonResult.toString();
    }

    private void deliverFuturesMarket(Session session, String message) {
        if (!sessionQueue.contains(session)) {
            logger.info("当前在线人数：" + onlineCount);
            return;
        }
        String[] params = message.split(",");
        String futureID = params[1];
        String brokerName = params[2];

        String key = "orderBook," + brokerName + "," + futureID;
        System.out.println("key=" + key);
        String result = getFuturesMarketFromRedis(key);
        sendMessage(session, result);
        logger.info("服务端返回: " + result);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            onClose(session);
        }
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
