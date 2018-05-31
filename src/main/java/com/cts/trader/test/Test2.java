package com.cts.trader.test;

import com.cts.trader.websocket.SocketClientEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Test2 {
    private Test test;
    private SocketClientEngine engine;

    @Autowired
    public Test2(SocketClientEngine engine) throws Exception {
        //this.test = test;
        //this.test.testRedis();
        this.engine = engine;
        //Thread.sleep(5000);
        //this.engine.runWs();
    }
}
