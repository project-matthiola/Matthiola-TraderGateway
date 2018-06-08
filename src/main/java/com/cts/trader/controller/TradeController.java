package com.cts.trader.controller;

import com.cts.trader.model.RestResult;
import com.cts.trader.service.TradeService;
import com.cts.trader.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description TradeController
 * @version 1.0.0
 **/
@RestController
@RequestMapping("/trade")
public class TradeController {
    private TradeService tradeService;
    private ResultGenerator resultGenerator;

    @Autowired
    public TradeController(TradeService tradeService, ResultGenerator resultGenerator) {
        this.tradeService = tradeService;
        this.resultGenerator = resultGenerator;
    }

    @GetMapping("/getTrades")
    public RestResult getTrades(@RequestParam("futuresID")String futuresID, @RequestParam("page")String page, HttpServletRequest request) {
        // return resultGenerator.getSuccessResult(tradeService.getTrades(futuresID, page, request));
        Map result = tradeService.getTrades(futuresID, page, request);
        return resultGenerator.getSuccessResult(String.valueOf(result.get("totalNum")), result.get("tradeList"));
    }
}
