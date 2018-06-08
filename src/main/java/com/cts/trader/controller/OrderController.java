package com.cts.trader.controller;

import com.cts.trader.model.Order;
import com.cts.trader.model.RestResult;
import com.cts.trader.service.OrderService;
import com.cts.trader.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description OrderController
 * @version 1.0.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private ResultGenerator resultGenerator;

    @Autowired
    public OrderController(OrderService orderService, ResultGenerator resultGenerator) {
        this.orderService = orderService;
        this.resultGenerator = resultGenerator;
    }

    @PostMapping("/sendOrder")
    public RestResult sendOrder(@RequestBody Order order, HttpServletRequest request) {
        boolean result = orderService.sendOrder(order, request);
        if (result) return resultGenerator.getSuccessResult();
        else return resultGenerator.getFailResult("fail");
    }

    @PostMapping("/cancelOrder")
    public RestResult cancelOrder(@RequestBody Order order, HttpServletRequest request) {
        boolean result = orderService.cancelOrder(order, request);
        if (result) return resultGenerator.getSuccessResult();
        else return resultGenerator.getFailResult("fail");
    }

    @GetMapping("/getOrders")
    public RestResult getOrders(@RequestParam("futuresID")String futuresID, @RequestParam("status")String status,
                                @RequestParam("page")String page, HttpServletRequest request) {
        //return resultGenerator.getSuccessResult(orderService.getOrders(futuresID, status, page, request));
        Map result = orderService.getOrders(futuresID, status, page, request);
        return resultGenerator.getSuccessResult(String.valueOf(result.get("totalNum")), result.get("orderList"));
    }

}
