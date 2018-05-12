package com.cts.trader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @Column(nullable = false, name = "order_id")
    private Long orderID;

    @Column(nullable = false, name = "future_id")
    private String futureID;

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "side")
    private String side;

    @Column(nullable = false, name = "price")
    private Float price;

    @Column(nullable = false, name = "quantity")
    private Integer quantity;

    @Column(nullable = false, name = "broker_name")
    private String brokerName;

    public Order() {}

    public Order(Long orderID, String futureID, String type, String side,
                 Float price, Integer quantity, String brokerName) {
        this.orderID = orderID;
        this.futureID = futureID;
        this.type = type;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.brokerName = brokerName;
    }


    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getFutureID() {
        return futureID;
    }

    public void setFutureID(String futureID) {
        this.futureID = futureID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }
}