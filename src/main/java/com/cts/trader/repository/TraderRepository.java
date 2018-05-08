package com.cts.trader.repository;

import com.cts.trader.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("TraderRepository")
public interface TraderRepository extends JpaRepository<Trader, Long> {
    Trader findTraderByTraderName(String traderName);
    Trader findTraderByTraderNameAndPassword(String traderName, String password);
}
