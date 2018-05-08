package com.cts.trader.model;

public enum ResultStatus {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private int status;

    ResultStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}
