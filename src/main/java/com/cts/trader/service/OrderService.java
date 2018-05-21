package com.cts.trader.service;

import com.cts.trader.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    /**
     * 发送订单
     * @param order     订单
     * @param request   http请求
     * @return boolean  发送结果
     */
    boolean sendOrder(Order order, HttpServletRequest request);

    /**
     * 取消订单
     * @param order     订单
     * @param request   http请求
     * @return boolean  取消结果
     */
    boolean cancelOrder(Order order, HttpServletRequest request);

    /**
     * 获取订单信息
     * @param futuresID 期货ID
     * @param status    订单状态
     * @param request   http请求
     * @return List     订单列表
     */
    List getOrders(String futuresID, String status, HttpServletRequest request);
}
