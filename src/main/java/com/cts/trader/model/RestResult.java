package com.cts.trader.model;

public class RestResult {
    //状态码
    private int status;
    //消息
    private String message;
    //额外内容
    private Object data;

    public RestResult() {}

    public RestResult setStatus(ResultStatus status){
        this.status = status.getStatus();
        return this;
    }

    public int getStatus(){
        return this.status;
    }

    public RestResult setStatus(int status){
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RestResult setData(Object data) {
        this.data = data;
        return this;
    }
}
