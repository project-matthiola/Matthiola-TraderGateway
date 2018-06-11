package com.cts.trader.repository;

import com.cts.trader.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description BrokerRepository
 * @version 1.0.0
 **/
@Repository("BrokerRepository")
public interface BrokerRepository extends JpaRepository<Broker, Long> {
    /**
     * 通过brokerName获取Broker
     * @param brokerName
     * @return Broker
     */
    Broker findBrokerByBrokerName(String brokerName);
    Broker findBrokerByBrokerID(String brokerID);
}
