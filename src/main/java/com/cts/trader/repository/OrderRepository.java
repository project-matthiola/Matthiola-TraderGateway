package com.cts.trader.repository;

import com.cts.trader.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("OrderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByOrderID(Long orderID);
}
