package com.cts.trader.service.impl;

import com.cts.trader.model.Broker;
import com.cts.trader.repository.BrokerRepository;
import com.cts.trader.service.TradeService;
import com.cts.trader.utils.HttpUtil;
import com.cts.trader.utils.JwtTokenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvjiawei
 * @date 2018/6/5
 * @description TradeService实现
 * @version 1.0.0
 **/
@Service("tradeService")
public class TradeServiceImpl implements TradeService {
    private JwtTokenUtil jwtTokenUtil;
    private BrokerRepository brokerRepository;

    @Autowired
    public TradeServiceImpl(BrokerRepository brokerRepository, JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.brokerRepository = brokerRepository;
    }

    @Override
    public List getTrades(String futuresID, String page, HttpServletRequest request) {
        String username = jwtTokenUtil.parseUsername(request);
        String params = "";
        if (!futuresID.equals("null")) params = params + "futures_id=" + futuresID;
        params = params + "&trader_name=" + username;
        params = params + "&page=" + page;

        System.out.println(params);

        List<Broker> brokers = brokerRepository.findAll();

        List tradeList = new ArrayList();
        for (Broker broker : brokers) {
            String result = new HttpUtil().sendGet(broker.getBrokerHttp() + "/trades", null, broker.getBrokerToken());
            JSONObject jsonResult = JSONObject.fromObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                object.put("broker", broker.getBrokerName());
                tradeList.add(object);
            }
        }

        return tradeList;
    }
}
