package com.cts.trader.service.impl;

import com.cts.trader.model.Order;
import com.cts.trader.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quickfix.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public void sendOrder(Order order) {
        Message message = new Message();
        message.getHeader().setField(new StringField(8, "FIX.4.4"));
        message.getHeader().setField(new StringField(49, "TraderGateway"));
        message.getHeader().setField(new StringField(56, order.getBrokerName()));
        message.getHeader().setField(new CharField(35, 'F'));
        message.setField(new StringField(16, order.getType()));
        try {
            Session.sendToTarget(message);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }

}
