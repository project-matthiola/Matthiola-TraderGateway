package com.cts.trader.service.impl;

import com.cts.trader.model.Future;
import com.cts.trader.repository.FutureRepository;
import com.cts.trader.service.FutureService;
import com.cts.trader.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("FutureService")
public class FutureServiceImpl implements FutureService {
    /*
    @Autowired
    private FutureRepository futureRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    */

    private FutureRepository futureRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public FutureServiceImpl(FutureRepository futureRepository, JwtTokenUtil jwtTokenUtil) {
        this.futureRepository = futureRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public List findAllFutures() {
        List<Future> futureList = futureRepository.findAll();
        List futures = new ArrayList();
        Set<String> futuresNameSet = new HashSet<>();
        for (Future future : futureList) {
            futuresNameSet.add(future.getFutureName());
        }

        for (String futureName : futuresNameSet) {
            Map map = new HashMap();
            map.put("futureName", futureName);

            List<Future> specificFutures = futureRepository.findFuturesByFutureName(futureName);
            List futureInfoList = new ArrayList();
            for (Future specificFuture : specificFutures) {
                Map periodInfo = new HashMap();
                periodInfo.put("value", specificFuture.getFutureID());
                periodInfo.put("period", specificFuture.getPeriod() + "期");
                futureInfoList.add(periodInfo);
            }
            map.put("futureInfo", futureInfoList);
            map.put("select", "");
            futures.add(map);
        }
        return futures;
    }

    @Override
    public Future findFutureByFutureID(String futureID) {
        return futureRepository.findFutureByFutureID(futureID);
    }

    @Override
    public List findFuturesByFutureName(String futureName) {
        return futureRepository.findFuturesByFutureName(futureName);
    }
}
