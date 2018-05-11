package com.cts.trader.controller;

import com.cts.trader.model.RestResult;
import com.cts.trader.service.FutureService;
import com.cts.trader.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/future")
public class FutureController {
    private FutureService futureService;
    private ResultGenerator resultGenerator;

    @Autowired
    public FutureController(FutureService futureService, ResultGenerator resultGenerator) {
        this.futureService = futureService;
        this.resultGenerator = resultGenerator;
    }

    @GetMapping("/getAllFutures")
    public RestResult getAllFutures() {
        return resultGenerator.getSuccessResult(futureService.findAllFutures());
    }

    @GetMapping("/getFutureByFutureID")
    public RestResult getFutureByFutureID(@RequestParam("futureID")String futureID) {
        return resultGenerator.getSuccessResult(futureService.findFutureByFutureID(futureID));
    }

    @GetMapping("/getFuturesByFutureName")
    public RestResult getFuturesByFutureName(@RequestParam("futureName")String futureName) {
        return resultGenerator.getSuccessResult(futureService.findFuturesByFutureName(futureName));
    }
}
