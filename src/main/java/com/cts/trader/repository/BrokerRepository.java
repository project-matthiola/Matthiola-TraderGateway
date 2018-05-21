package com.cts.trader.repository;

import com.cts.trader.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BrokerRepository")
public interface BrokerRepository extends JpaRepository<Broker, Long> {
    Broker findBrokerByBrokerName(String brokerName);
}
