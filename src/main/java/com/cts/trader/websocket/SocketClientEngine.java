package com.cts.trader.websocket;

import com.cts.trader.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.util.Queue;

//@Component(value = "SocketClientEngine")
public class SocketClientEngine {
    private GatewaySocketClient client;

    @Autowired
    public SocketClientEngine(GatewaySocketClient client) throws Exception {
        this.client = client;
        //this.client.connect2Url("ws://localhost:4869/mockServer");
        runWs();
    }

    public void runWs() throws Exception {
        this.client.connect2Url("ws://localhost:4869/mockServer");
        //while (true) {
            //Thread.sleep(2000);
        this.client.sendMessage("abcabc");
        //}
    }

    public static void main(String[] args) throws Exception {
        //SocketClientEngine clientEngine = new SocketClientEngine();
        SocketClientEngine engine = (SocketClientEngine)ContextLoader.getCurrentWebApplicationContext().getBean("SocketClientEngine");
        //SocketClientEngine engine = (SocketClientEngine)SpringUtil.getBean("SocketClientEngine");
        engine.runWs();
    }
}
