package com.cts.trader.service;

import com.cts.trader.model.Future;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description FutureService接口
 * @version 1.0.0
 **/
public interface FutureService {
    /**
     * 查找所有future信息
     * @return future列表
     */
    List findAllFutures();

    /**
     * 获取futures级联列表
     * @return futures级联列表
     */
    List getFuturesCascader();

    /**
     * 查找指定id的future信息
     * @param futureID id
     * @return future信息
     */
    Future findFutureByFutureID(String futureID);

    /**
     * 查找指定名称的future信息
     * @param futureName 名称
     * @return future列表
     */
    List findFuturesByFutureName(String futureName);
}
