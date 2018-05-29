package com.cts.trader.service;

import java.util.List;

public interface BrokerService {
    /**
     * 查找所有broker信息(ID、名称)
     *
     * @return broker信息列表
     */
    List findAllBrokers();
}
