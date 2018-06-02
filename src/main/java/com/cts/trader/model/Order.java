package com.cts.trader.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order implements Serializable {
    private UUID orderID;

    private String futureID;

    private char type;

    private char side;

    private Double price;

    private Double price2;

    private Double amount;

    private String brokerName;

    private LocalDateTime timeStamp;

    public Order() {}

    public Order(UUID orderID, String futureID, char type, char side,
                 Double price, Double price2, Double amount, String brokerName, LocalDateTime timeStamp) {
        this.orderID = orderID;
        this.futureID = futureID;
        this.type = type;
        this.side = side;
        this.price = price;
        this.price2 = price;
        this.amount = amount;
        this.brokerName = brokerName;
        this.timeStamp = timeStamp;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public void setOrderID(UUID orderID) {
        this.orderID = orderID;
    }

    public String getFutureID() {
        return futureID;
    }

    public void setFutureID(String futureID) {
        this.futureID = futureID;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getPrice2() {
        return price2;
    }

    public void setPrice2(Double price2) {
        this.price2 = price2;
    }
}
