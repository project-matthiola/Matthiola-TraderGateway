package com.cts.trader.service.impl;

import com.cts.trader.model.Order;
import com.cts.trader.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quickfix.Message;
import quickfix.StringField;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    /*
    @Override
    public void sendOrder(Order order) {
        Message message = new Message();
        message.getHeader().setField(new StringField(8, "FIX.4.4"));
    }
    */
}
