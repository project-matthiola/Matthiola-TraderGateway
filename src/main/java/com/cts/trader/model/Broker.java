package com.cts.trader.model;

import javax.persistence.*;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description Broker
 * @version 1.0.0
 **/
@Entity
@Table(name = "Broker")
public class Broker {
    @Id
    @Column(nullable = false, name = "broker_id")
    private String brokerID;

    @Column(nullable = false, name = "broker_name")
    private String brokerName;

    @Column(name = "broker_http")
    private String brokerHttp;

    @Column(name = "broker_ws")
    private String brokerWs;

    @Column(name = "broker_token")
    private String brokerToken;

    public Broker() {}

    public Broker(String brokerID, String brokerName, String brokerHttp, String brokerWs, String brokerToken) {
        this.brokerID = brokerID;
        this.brokerName = brokerName;
        this.brokerHttp = brokerHttp;
        this.brokerWs = brokerWs;
        this.brokerToken = brokerToken;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerToken() {
        return brokerToken;
    }

    public void setBrokerToken(String brokerToken) {
        this.brokerToken = brokerToken;
    }

    public String getBrokerHttp() {
        return brokerHttp;
    }

    public void setBrokerHttp(String brokerHttp) {
        this.brokerHttp = brokerHttp;
    }

    public String getBrokerWs() {
        return brokerWs;
    }

    public void setBrokerWs(String brokerWs) {
        this.brokerWs = brokerWs;
    }
}
