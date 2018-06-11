package com.cts.trader.service.impl;

import com.cts.trader.model.Broker;
import com.cts.trader.model.Order;
import com.cts.trader.repository.BrokerRepository;
import com.cts.trader.service.OrderService;
import com.cts.trader.utils.HttpUtil;
import com.cts.trader.utils.JwtTokenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix50sp2.NewOrderSingle;
import quickfix.fix50sp2.OrderCancelRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description OrderService实现
 * @version 1.0.0
 **/
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private JwtTokenUtil jwtTokenUtil;
    private BrokerRepository brokerRepository;

    @Autowired
    public OrderServiceImpl(BrokerRepository brokerRepository, JwtTokenUtil jwtTokenUtil) {
        this.brokerRepository = brokerRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Async
    public void iceburgOrder(Order order, String username) {
        try {
            Thread.sleep(2000);
            Double qty = order.getAmount();
            Double sent = 0.0;
            Double step = qty / (60 * 60 * 8);
            for (; sent < qty; sent += step) {
                Thread.sleep(60 * 1000);

                NewOrderSingle orderSingle = new NewOrderSingle();
                orderSingle.getHeader().setField(new MsgType(MsgType.ORDER_SINGLE));
                orderSingle.getHeader().setField(new SenderCompID("Trader"));
                orderSingle.getHeader().setField(new TargetCompID("Broker"));
                orderSingle.getHeader().setField(new SenderSubID(username));
                orderSingle.set(new ClOrdID(UUID.randomUUID().toString()));
                orderSingle.set(new OrdType(order.getType()));
                orderSingle.set(new Side(order.getSide()));
                orderSingle.set(new Symbol(order.getFutureID()));
                orderSingle.set(new OrderQty(step));
                orderSingle.set(new Price(order.getPrice()));
                orderSingle.set(new TransactTime(LocalDateTime.now(ZoneId.of("UTC"))));

                try {
                    Session.sendToTarget(orderSingle);
                } catch (SessionNotFound e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendOrder(Order order, HttpServletRequest request) {
        String username = jwtTokenUtil.parseUsername(request);
        if (username == null) return false;

        order.setOrderID(UUID.randomUUID());
        order.setTimeStamp(LocalDateTime.now(ZoneId.of("UTC")));

        /*
        if (order.getAmount() > 10000.0) {
            iceburgOrder(order, username);
            return true;
        }
        */
        String Target = brokerRepository.findBrokerByBrokerID(order.getBrokerName()).getBrokerName();
        NewOrderSingle orderSingle = new NewOrderSingle();
        orderSingle.getHeader().setField(new MsgType(MsgType.ORDER_SINGLE));
        orderSingle.getHeader().setField(new SenderCompID("Trader"));
        orderSingle.getHeader().setField(new TargetCompID(Target));
        orderSingle.getHeader().setField(new SenderSubID(username));
        orderSingle.set(new ClOrdID(order.getOrderID().toString()));
        orderSingle.set(new OrdType(order.getType()));
        orderSingle.set(new Side(order.getSide()));
        orderSingle.set(new Symbol(order.getFutureID()));
        orderSingle.set(new OrderQty(order.getAmount()));
        orderSingle.set(new Price(order.getPrice()));
        orderSingle.set(new StopPx(order.getPrice2()));
        orderSingle.set(new TransactTime(order.getTimeStamp()));

        try {
            return Session.sendToTarget(orderSingle);
        } catch (SessionNotFound e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelOrder(Order order, HttpServletRequest request) {
        String username = jwtTokenUtil.parseUsername(request);
        if (username == null) return false;

        String Target = order.getBrokerName();
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.getHeader().setField(new MsgType(MsgType.ORDER_CANCEL_REQUEST));
        orderCancelRequest.getHeader().setField(new SenderCompID("Trader"));
        orderCancelRequest.getHeader().setField(new TargetCompID(Target));
        orderCancelRequest.getHeader().setField(new SenderSubID(username));
        orderCancelRequest.setField(new ClOrdID(UUID.randomUUID().toString()));
        orderCancelRequest.setField(new OrderID(order.getOrderID().toString()));
        orderCancelRequest.setField(new Symbol(order.getFutureID()));

        /*
        Message cancelOrderRequest = new Message();
        cancelOrderRequest.getHeader().setField(new MsgType(MsgType.ORDER_CANCEL_REQUEST));
        cancelOrderRequest.getHeader().setField(new SenderCompID("Trader"));
        cancelOrderRequest.getHeader().setField(new TargetCompID("Broker"));
        cancelOrderRequest.getHeader().setField(new SenderSubID(username));
        cancelOrderRequest.setField(new ClOrdID(UUID.randomUUID().toString()));
        cancelOrderRequest.setField(new OrderID(order.getOrderID().toString()));
        cancelOrderRequest.setField(new Symbol(order.getFutureID()));
        */
        try {
            return Session.sendToTarget(orderCancelRequest);
        } catch (SessionNotFound e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map getOrders(String futuresID, String status, String page, HttpServletRequest request) {
        String username = jwtTokenUtil.parseUsername(request);
        String params = "";
        if (!futuresID.equals("null")) params = params + "&futures_id=" + futuresID;
        if (!status.equals("-1")) params = params + "&status=" + status;
        params = params + "&trader_name=" + username;
        params = params + "&page=" + page;

        System.out.println(params);

        List<Broker> brokers = brokerRepository.findAll();
        int totalNum = 0;
        List orderList = new ArrayList();
        for (Broker broker : brokers) {
            String result = new HttpUtil().sendGet(broker.getBrokerHttp() + "/orders", params, broker.getBrokerToken());
            JSONObject jsonResult = JSONObject.fromObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("data");
            totalNum += jsonResult.getInt("total");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                object.put("brokerName", broker.getBrokerName());
                orderList.add(object);
            }
        }

        Map resultMap = new HashMap();
        resultMap.put("orderList", orderList);
        resultMap.put("totalNum", totalNum);
        return resultMap;
    }
}
