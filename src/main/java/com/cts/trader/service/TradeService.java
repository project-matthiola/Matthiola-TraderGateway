package com.cts.trader.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TradeService {
    /**
     * 获取交易记录
     * @param futuresID 期货ID
     * @param request   http请求
     * @return List     交易记录列表
     */
    List getTrades(String futuresID, HttpServletRequest request);
}
