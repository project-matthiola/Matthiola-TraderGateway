package com.cts.trader.utils;

import com.cts.trader.model.ResultStatus;
import com.cts.trader.model.RestResult;
import org.springframework.stereotype.Component;

@Component
public class ResultGenerator {
    private static final String SUCCESS = "success";

    //成功
    public RestResult getSuccessResult() {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(SUCCESS);
    }

    //成功，附带额外数据
    public RestResult getSuccessResult(Object data) {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }

    //成功，自定义消息及数据
    public RestResult getSuccessResult(String message,Object data) {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(message)
                .setData(data);
    }

    //失败，附带消息
    public RestResult getFailResult(String message) {
        return new RestResult()
                .setStatus(ResultStatus.FAIL)
                .setMessage(message);
    }

    //失败，自定义消息及数据
    public RestResult getFailResult(String message, Object data) {
        return new RestResult()
                .setStatus(ResultStatus.FAIL)
                .setMessage(message)
                .setData(data);
    }

    //自定义创建
    public RestResult getFreeResult(ResultStatus code, String message, Object data) {
        return new RestResult()
                .setStatus(code)
                .setMessage(message)
                .setData(data);
    }
}
