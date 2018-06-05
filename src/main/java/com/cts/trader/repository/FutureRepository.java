package com.cts.trader.repository;

import com.cts.trader.model.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description FutureRepository
 * @version 1.0.0
 **/
@Repository("FutureRepository")
public interface FutureRepository extends JpaRepository<Future, String> {
    /**
     * 通过futureID获取Future
     * @param futureID
     * @return Future
     */
    Future findFutureByFutureID(String futureID);

    /**
     * 通过futureName获取Future列表
     * @param futureName
     * @return Future列表
     */
    List<Future> findFuturesByFutureName(String futureName);

    /**
     * 获取未过期的Future列表
     * @param expired
     * @return Future列表
     */
    List<Future> findFuturesByExpired(String expired);

    /**
     * 通过futureName获取未过期的Future列表
     * @param futureName
     * @param expired
     * @return Future列表
     */
    List<Future> findFuturesByFutureNameAndExpired(String futureName, String expired);
}
