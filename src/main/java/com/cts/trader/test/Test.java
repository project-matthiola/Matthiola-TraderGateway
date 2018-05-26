package com.cts.trader.test;

import com.cts.trader.model.Future;
import com.cts.trader.utils.SpringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

public class Test {
    @Resource(name = "myRedisTemplate")
    private RedisTemplate redisTemplate;

    /*
    @Autowired
    public Test(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    */

    public void testRedis() throws Exception {
        Future future = new Future();
        future.setFutureID("GU");
        future.setFutureName("abc");
        future.setCategory("aaa");
        future.setExpired("bbb");
        future.setPeriod("ccc");
        future.setSymbol("ddd");
        redisTemplate.opsForValue().set("future2", future);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("name", "2");
        jsonArray.add(jsonObject);
        redisTemplate.opsForValue().set("testJson4", jsonArray.toString());
        Thread.sleep(1000);

        boolean exist = redisTemplate.hasKey("testJson4");
        if (exist) {
            System.out.println("exits," + redisTemplate.opsForValue().get("testJson4"));
        } else {
            System.out.println("not exits");
        }
    }

    public static void main(String[] args) throws Exception {
        //Test test = new Test();
        //test.testRedis();
    }

}
