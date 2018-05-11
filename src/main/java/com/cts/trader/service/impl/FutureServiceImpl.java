package com.cts.trader.service.impl;

import com.cts.trader.model.Future;
import com.cts.trader.repository.FutureRepository;
import com.cts.trader.service.FutureService;
import com.cts.trader.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        return futureRepository.findAll();
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
