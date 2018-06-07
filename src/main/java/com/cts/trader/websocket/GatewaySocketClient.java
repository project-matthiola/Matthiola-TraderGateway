package com.cts.trader.websocket;

import com.cts.trader.utils.SpringUtil;
import com.cts.trader.utils.TradeHistoryComparator;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description WebsocketClient
 * @version 1.0.0
 **/
@ClientEndpoint
@Component
public class GatewaySocketClient {
    // 日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 服务端session
    private Session session;

    // Redis连接
    private RedisTemplate redisTemplate;

    // 全局变量，记录session和名称的映射
    private static Map<Session, String> brokerMapping = new ConcurrentHashMap<>();

    private Map<String, Map<String, List<List>>> orderBook = new HashMap<>();

    public void setBrokerMapping(String brokerName) {
        brokerMapping.put(this.session, brokerName);
    }

    private Map getBrokerMapping() {
        return brokerMapping;
    }


    public static GatewaySocketClient connect(String url) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        GatewaySocketClient client = new GatewaySocketClient();
        container.connectToServer(client, new URI(url));
        return client;
    }

    public void connect2Url(String url) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(GatewaySocketClient.class, new URI(url));
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        logger.info("Client on open");
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.info("client on close");
        logger.warn(throwable.getMessage());
    }

    @OnClose
    public void onClose() throws IOException {
        this.session.close();
    }

    /*
    @OnMessage
    public void onMessage(String message) throws Exception {
        logger.info("client on message" + message);

        redisTemplate = (RedisTemplate)SpringUtil.getBean("myRedisTemplate");

        JSONObject jsonObject = JSONObject.fromObject(message);
        Set<String> keySet = jsonObject.keySet();
        List<String> jsonKeys = new ArrayList<String>();
        for (String key : keySet) {
            jsonKeys.add(key);
        }

        String type = jsonObject.getString(jsonKeys.get(0));
        String futuresID = jsonObject.getString(jsonKeys.get(1));
        String redisKey = "";
        switch (type) {
            case "orderBook":
                redisKey = "orderBook," + brokerMapping.get(session) + "," + futuresID;
                JSONObject data = jsonObject.getJSONObject(jsonKeys.get(2));
                redisTemplate.opsForValue().set(redisKey, data.toString());
                break;

            case "trade":
                redisKey = "trade," + brokerMapping.get(session) + "," + futuresID;
                JSONArray data2 = jsonObject.getJSONArray(jsonKeys.get(2));
                redisTemplate.opsForValue().set(redisKey, data2.toString());
                break;
        }

        Thread.sleep(10000);
        sendMessage("heartbeat");
    }
    */

    @OnMessage
    public void onMessage(String message) throws Exception {
        logger.info("on message:" + message);
        redisTemplate = (RedisTemplate)SpringUtil.getBean("myRedisTemplate");

        JSONObject jsonMsg = JSONObject.fromObject(message);
        Set<String> keySet = jsonMsg.keySet();
        List<String> jsonKeys = new ArrayList<>(keySet);

        String type = jsonMsg.getString(jsonKeys.get(0)); // 'orderBook','trade'
        String mode = jsonMsg.getString(jsonKeys.get(1)); // 'snapshot','update'
        String futuresID = jsonMsg.getString(jsonKeys.get(2));
        String redisKey = "";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z z");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        switch (type) {
            case "trade":
                redisKey = "trade," + brokerMapping.get(session) + "," + futuresID;
                if (mode.equals("snapshot")) {
                    JSONArray data = jsonMsg.getJSONArray(jsonKeys.get(3));
                    JSONArray resultArray = new JSONArray();
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject tmpObj = data.getJSONObject(i);
                        JSONArray tmpArray = new JSONArray();
                        tmpArray.add(tmpObj.get("price"));
                        tmpArray.add(tmpObj.get("quantity"));
                        String timeStr = tmpObj.getString("time");
                        String formatTime = sdf2.format(sdf1.parse(timeStr));
                        tmpArray.add(formatTime);
                        resultArray.add(tmpArray);
                    }
                    redisTemplate.opsForValue().set(redisKey, resultArray.toString());
                } else if (mode.equals("update")) {
                    JSONObject updateData = jsonMsg.getJSONObject(jsonKeys.get(3));
                    JSONArray updateArray = new JSONArray();
                    updateArray.add(updateData.getString("price"));
                    updateArray.add(updateData.getString("quantity"));
                    String timeStr = updateData.getString("time");
                    String formatTime = sdf2.format(sdf1.parse(timeStr));
                    updateArray.add(formatTime);

                    JSONArray formerArray = JSONArray.fromObject(redisTemplate.opsForValue().get(redisKey));
                    List<JSONArray> tradeList = new ArrayList<>();
                    for (int i = 0; i < formerArray.size(); i++) {
                        tradeList.add(formerArray.getJSONArray(i));
                    }
                    Collections.sort(tradeList, new TradeHistoryComparator());

                    if (tradeList.size() >= 50) {
                        tradeList.remove(tradeList.size() - 1);
                        tradeList.add(updateArray);
                    } else {
                        tradeList.add(updateArray);
                    }
                    Collections.sort(tradeList, new TradeHistoryComparator());

                    JSONArray updatedArray = JSONArray.fromObject(tradeList);
                    redisTemplate.opsForValue().set(redisKey, updatedArray.toString());
                }
                break;
            case "order_book":
                redisKey = "orderBook," + brokerMapping.get(session) + "," + futuresID;
                if (mode.equals("snapshot")) {
                    JSONObject data = jsonMsg.getJSONObject(jsonKeys.get(3));
                    redisTemplate.opsForValue().set(redisKey, data.toString());
                }
                break;
                /*
                redisKey = "orderBook," + brokerMapping.get(session) + "," + futuresID;
                if (mode.equals("snapshot")) {
                    JSONObject data = jsonMsg.getJSONObject(jsonKeys.get(3));
                    redisTemplate.opsForValue().set(redisKey, data.toString());
                } else if (mode.equals("update")) {
                    JSONArray changes = jsonMsg.getJSONArray(jsonKeys.get(3));

                    JSONObject former = JSONObject.fromObject(redisTemplate.opsForValue()
                    .get(redisKey));
                    JSONArray formerBids = former.getJSONArray("bids");
                    JSONArray formerAsks = former.getJSONArray("asks");

                    Map<String, String> bidsMap = new HashMap<>();
                    Map<String, String> asksMap = new HashMap<>();

                    for (int i = 0; i < formerBids.size(); i++) {
                        JSONArray tmpArray = formerBids.getJSONArray(i);
                        bidsMap.put(tmpArray.getString(0), tmpArray.getString(1));
                    }
                    for (int i = 0; i < formerAsks.size(); i++) {
                        JSONArray tmpArray = formerAsks.getJSONArray(i);
                        asksMap.put(tmpArray.getString(0), tmpArray.getString(1));
                    }

                    for (int i = 0; i < changes.size(); i++) {
                        JSONArray tmpArray = changes.getJSONArray(i);
                        String side = tmpArray.getString(0);
                        String price = tmpArray.getString(1);
                        String qty = tmpArray.getString(2);

                        if (side.equals("sell")) {
                            if (qty.equals("0")) {
                                asksMap.remove(price);
                            } else {
                                asksMap.replace(price, qty);
                            }
                        } else if (side.equals("buy")) {
                            if (qty.equals("0")) {
                                bidsMap.remove(price);
                            } else {
                                bidsMap.replace(price, qty);
                            }
                        }
                    }

                    JSONArray newBids = new JSONArray();
                    JSONArray newAsks = new JSONArray();

                    Iterator iter1 = bidsMap.entrySet().iterator();
                    while (iter1.hasNext()) {
                        Map.Entry entry1 = (Map.Entry)iter1.next();
                        JSONArray tmp = new JSONArray();
                        tmp.add(entry1.getKey());
                        tmp.add(entry1.getValue());
                        newBids.add(tmp);
                    }
                    Iterator iter2 = asksMap.entrySet().iterator();
                    while (iter2.hasNext()) {
                        Map.Entry entry2 = (Map.Entry)iter2.next();
                        JSONArray tmp = new JSONArray();
                        tmp.add(entry2.getKey());
                        tmp.add(entry2.getValue());
                        newAsks.add(tmp);
                    }

                    JSONObject newer = new JSONObject();
                    newer.put("bids", newBids);
                    newer.put("asks", newAsks);

                    redisTemplate.opsForValue().set(redisKey, newer.toString());
                }
                break;
                */

        }
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
