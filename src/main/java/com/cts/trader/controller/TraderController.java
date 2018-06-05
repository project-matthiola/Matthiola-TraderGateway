package com.cts.trader.controller;

import com.cts.trader.model.RestResult;
import com.cts.trader.model.Trader;
import com.cts.trader.service.TraderService;
import com.cts.trader.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description TraderController
 * @version 1.0.0
 **/
@RestController
@RequestMapping("/trader")
@Validated
public class TraderController {
    private TraderService traderService;
    private ResultGenerator resultGenerator;

    @Autowired
    public TraderController(TraderService traderService, ResultGenerator resultGenerator) {
        this.traderService = traderService;
        this.resultGenerator = resultGenerator;
    }

    @GetMapping("/getAllTraders")
    public RestResult getAllTraders() {
        return resultGenerator.getSuccessResult(traderService.findAllTraders());
    }

    @PostMapping("/register")
    public RestResult register(@RequestBody @Valid Trader trader, BindingResult bindingResult) throws AuthenticationException {
        boolean check = traderService.checkDuplicate(trader);
        if(check) return resultGenerator.getFailResult("用户已存在");
        else return resultGenerator.getSuccessResult("用户注册成功", traderService.register(trader));
    }

    @PostMapping("/login")
    public RestResult login(@RequestBody Trader trader) throws AuthenticationException {
        String token = traderService.login(trader.getTraderName(), trader.getPassword());
        /*
        if(newTrader != null) {
            session.setAttribute("trader", newTrader);
            return resultGenerator.getSuccessResult("登录成功", newTrader);
        } else {
            return resultGenerator.getFailResult("用户名/密码错误");
        }
        */
        //Date expirationDate = new Date(System.currentTimeMillis() + 1800L * 1000);
        //String timestamp = String.valueOf(expirationDate.getTime());
        //System.out.println(timestamp);
        //return resultGenerator.getSuccessResult(timestamp, token);
        return resultGenerator.getSuccessResult(token);
    }

    @GetMapping("/refreshToken")
    public RestResult refreshToken(@RequestHeader String authorization) throws AuthenticationException {
        String result = traderService.refreshToken(authorization);
        if (result.equals("error")) return resultGenerator.getFailResult("fail");
        else return resultGenerator.getSuccessResult(result);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleConstraintViolationException(ConstraintViolationException cve) {
        String errorMessage = cve.getConstraintViolations().iterator().next().getMessage();
        return resultGenerator.getFailResult(errorMessage);
    }
}
