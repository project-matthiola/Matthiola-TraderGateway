package com.cts.trader.service.impl;

import com.cts.trader.model.Future;
import com.cts.trader.repository.FutureRepository;
import com.cts.trader.service.FutureService;
import com.cts.trader.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(cacheNames = "futureslist")
    @Override
    public List findAllFutures() {
        //List<Future> futureList = futureRepository.findAll();
        List<Future> futureList = futureRepository.findFuturesByExpired("false");
        List futures = new ArrayList();
        Set<String> futuresNameSet = new HashSet<>();
        for (Future future : futureList) {
            futuresNameSet.add(future.getFutureName());
        }

        for (String futureName : futuresNameSet) {
            Map map = new HashMap();
            map.put("futureName", futureName);

            //List<Future> specificFutures = futureRepository.findFuturesByFutureName(futureName);
            List<Future> specificFutures = futureRepository.findFuturesByFutureNameAndExpired(futureName, "false");
            List futureInfoList = new ArrayList();
            for (Future specificFuture : specificFutures) {
                Map periodInfo = new HashMap();
                periodInfo.put("value", specificFuture.getFutureID());
                periodInfo.put("period", specificFuture.getPeriod() + " Period");
                futureInfoList.add(periodInfo);
            }
            map.put("futureInfo", futureInfoList);
            map.put("select", "");
            futures.add(map);
        }
        return futures;
    }

    @Override
    public List getFuturesCascader() {
        List<Future> futureList = futureRepository.findAll();
        List futuresCascader = new ArrayList();
        Set<String> futuresNameSet = new HashSet<>();
        for (Future future : futureList) {
            futuresNameSet.add(future.getFutureName());
        }

        for (String futuresName : futuresNameSet) {
            Map map = new HashMap();
            map.put("value", futuresName);
            map.put("label", futuresName);

            List<Future> specificFutures = futureRepository.findFuturesByFutureName(futuresName);
            List childrenList = new ArrayList();
            for (Future specificFuture : specificFutures) {
                Map children = new HashMap();
                children.put("value", specificFuture.getFutureID());
                children.put("label", specificFuture.getPeriod() + "Period");
                childrenList.add(children);
            }
            map.put("children", childrenList);
            futuresCascader.add(map);
        }
        return futuresCascader;
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
