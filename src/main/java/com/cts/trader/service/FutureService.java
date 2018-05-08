package com.cts.trader.service;

import com.cts.trader.model.Future;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FutureService {
    /**
     * 查找所有future信息
     *
     * @param request http请求
     * @return future列表
     */
    List findAllFutures(HttpServletRequest request);

    /**
     * 查找指定id的future信息
     *
     * @param futureID id
     * @return future信息
     */
    Future findFutureByFutureID(String futureID);
}
