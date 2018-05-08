package com.cts.trader.repository;

import com.cts.trader.model.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("FutureRepository")
public interface FutureRepository extends JpaRepository<Future, String> {
    Future findFutureByFutureID(String futureID);
}
