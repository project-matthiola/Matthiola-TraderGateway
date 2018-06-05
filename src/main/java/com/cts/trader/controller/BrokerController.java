package com.cts.trader.controller;

import com.cts.trader.model.RestResult;
import com.cts.trader.service.BrokerService;
import com.cts.trader.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description BrokerController
 * @version 1.0.0
 **/
@RestController
@RequestMapping("/broker")
public class BrokerController {
    private BrokerService brokerService;
    private ResultGenerator resultGenerator;

    @Autowired
    public BrokerController(BrokerService brokerService, ResultGenerator resultGenerator) {
        this.brokerService = brokerService;
        this.resultGenerator = resultGenerator;
    }

    @GetMapping("/getAllBrokers")
    public RestResult getAllBrokers() {
        return resultGenerator.getSuccessResult(brokerService.findAllBrokers());
    }
}
