package com.cts.trader.repository;

import com.cts.trader.model.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FutureRepository")
public interface FutureRepository extends JpaRepository<Future, String> {
    Future findFutureByFutureID(String futureID);
    List<Future> findFuturesByFutureName(String futureName);
}
