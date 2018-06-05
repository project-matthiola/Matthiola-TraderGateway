package com.cts.trader.service;

import java.util.List;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description BrokerService接口
 * @version 1.0.0
 **/
public interface BrokerService {
    /**
     * 查找所有broker信息(ID、名称)
     * @return broker信息列表
     */
    List findAllBrokers();
}
