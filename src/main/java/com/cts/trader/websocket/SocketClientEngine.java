package com.cts.trader.websocket;

import com.cts.trader.model.Broker;
import com.cts.trader.repository.BrokerRepository;
import com.cts.trader.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description WebsocketClientEngine
 * @version 1.0.0
 **/
@Component(value = "SocketClientEngine")
public class SocketClientEngine {
    private final String traderGatewayName = "TraderGateway1";
    private BrokerRepository brokerRepository;

    @Autowired
    public SocketClientEngine(BrokerRepository brokerRepository) throws Exception {
        this.brokerRepository = brokerRepository;
        List<Broker> brokers = this.brokerRepository.findAll();
        List<String> brokerNames = new ArrayList<>();
        List<String> brokerWs = new ArrayList<>();
        for (Broker broker : brokers) {
            brokerNames.add(broker.getBrokerID());
            brokerWs.add(broker.getBrokerWs());
        }

        for (int i = 0; i < brokerNames.size(); i++) {
            GatewaySocketClient client = new GatewaySocketClient();
            client.connect2Url(brokerWs.get(i));
            client.setBrokerMapping(brokerNames.get(i));
            client.sendMessage("I am " + traderGatewayName);
        }
    }
}
