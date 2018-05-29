package com.cts.trader.service.impl;

import com.cts.trader.service.TradeService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("tradeService")
public class TradeServiceImpl implements TradeService {
    @Override
    public List getTrades(String futuresID, String selfOnly, HttpServletRequest request) {
        return null;
    }
}
