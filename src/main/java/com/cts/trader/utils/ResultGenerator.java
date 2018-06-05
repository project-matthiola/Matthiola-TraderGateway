package com.cts.trader.utils;

import com.cts.trader.model.ResultStatus;
import com.cts.trader.model.RestResult;
import org.springframework.stereotype.Component;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description Rest结果生成器
 * @version 1.0.0
 **/
@Component
public class ResultGenerator {
    private static final String SUCCESS = "success";

    /**
     * 成功
     * @return RestResult
     */
    public RestResult getSuccessResult() {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(SUCCESS);
    }

    /**
     * 成功，附带数据
     * @param data 数据
     * @return RestResult
     */
    public RestResult getSuccessResult(Object data) {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }

    /**
     * 成功，附带消息和数据
     * @param message 消息
     * @param data 数据
     * @return RestResult
     */
    public RestResult getSuccessResult(String message,Object data) {
        return new RestResult()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(message)
                .setData(data);
    }

    /**
     * 失败，附带消息
     * @param message 消息
     * @return RestResult
     */
    public RestResult getFailResult(String message) {
        return new RestResult()
                .setStatus(ResultStatus.FAIL)
                .setMessage(message);
    }

    /**
     * 失败，附带消息和数据
     * @param message 消息
     * @param data 数据
     * @return RestResult
     */
    public RestResult getFailResult(String message, Object data) {
        return new RestResult()
                .setStatus(ResultStatus.FAIL)
                .setMessage(message)
                .setData(data);
    }

    /**
     * 自定义返回结果
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     * @return RestResult
     */
    public RestResult getFreeResult(ResultStatus code, String message, Object data) {
        return new RestResult()
                .setStatus(code)
                .setMessage(message)
                .setData(data);
    }
}
