package com.cts.trader.repository;

import com.cts.trader.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description TraderRepository
 * @version 1.0.0
 **/
@Repository("TraderRepository")
public interface TraderRepository extends JpaRepository<Trader, Long> {
    /**
     * 通过traderName获取Trader
     * @param traderName
     * @return Trader
     */
    Trader findTraderByTraderName(String traderName);
    Trader findTraderByTraderNameAndPassword(String traderName, String password);
}
