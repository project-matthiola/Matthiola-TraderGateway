package com.cts.trader.websocket;

import com.cts.trader.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    private static Queue<Session> sessionQueue = new ConcurrentLinkedQueue<>();
    private static String DISCONNECT_KEY = "zuwBo*W^!7E#Jc1@";

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
        if (message.equals(DISCONNECT_KEY)) {
            onClose(session);
            return;
        }
        deliverFuturesMarket(session, message);
    }

    private void deliverFuturesMarket(Session session, String futureID) {
        if (!sessionQueue.contains(session)) {
            logger.info("当前在线人数：" + onlineCount);
            return;
        }

        String result = HttpUtil.sendGet("http://private-8a634-matthiola.apiary-mock.com/futures/" + futureID + "/book", null);
        JSONObject jsonResult = JSONObject.fromObject(result);
        JSONObject jsonData = jsonResult.getJSONObject("data");
        System.out.println(jsonData);
        String res = jsonData.toString();
        sendMessage(session, res);

        /*
        int total = (int)(Math.random() * 30);
        JSONArray jsonArraySells = new JSONArray();
        JSONArray jsonArrayBuys = new JSONArray();
        for (int i = 0;i < total;i++) {
            JSONObject json1 = new JSONObject();
            json1.put("id", i);
            json1.put("price", i * 0.9);
            json1.put("quantity", i * 100);
            jsonArraySells.add(json1);
        }
        total = (int)(Math.random() * 30);
        for (int i = 0;i < total;i++) {
            JSONObject json2 = new JSONObject();
            json2.put("id", i);
            json2.put("price", (30 - i) * 0.9);
            json2.put("quantity", i * 100);
            jsonArrayBuys.add(json2);
        }
        JSONObject orderBook = new JSONObject();
        orderBook.put("sells", jsonArraySells);
        orderBook.put("buys", jsonArrayBuys);

        String res = orderBook.toString();
        sendMessage(session, res);
        logger.info("服务端返回消息：" + res);
        */
        try {
            Thread.sleep(2000);
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
