package com.cts.trader.websocket;

import com.cts.trader.utils.SpringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Set;

@ClientEndpoint
@Component
public class GatewaySocketClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Session session;
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    private RedisTemplate redisTemplate;

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
    }

    @OnClose
    public void onClose() throws IOException {
        this.session.close();
    }

    @OnMessage
    public void onMessage(String message) throws Exception {
        logger.info("client on message" + message);

        redisTemplate = (RedisTemplate)SpringUtil.getBean("myRedisTemplate");

        JSONObject jsonObject = JSONObject.fromObject(message);
        Set<String> keySet = jsonObject.keySet();
        String marketKey = "";
        for (String key : keySet) {
            marketKey = key;
        }
        System.out.println(marketKey);
        JSONObject marketData = jsonObject.getJSONObject(marketKey);

        redisTemplate.opsForValue().set(marketKey, marketData.toString());

        Thread.sleep(2000);
        sendMessage("heartbeat");
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
