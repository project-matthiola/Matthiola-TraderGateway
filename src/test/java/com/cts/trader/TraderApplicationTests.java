package com.cts.trader;

import com.cts.trader.model.Future;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraderApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
    public void testRedis() throws Exception {
        Future future = new Future();
        future.setFutureID("GU");
        future.setFutureName("abc");
        future.setCategory("aaa");
        future.setExpired("bbb");
        future.setPeriod("ccc");
        future.setSymbol("ddd");
        redisTemplate.opsForValue().set("future1", future);
        Thread.sleep(1000);

        boolean exist = redisTemplate.hasKey("future1");
        if (exist) {
            System.out.println("exits," + redisTemplate.opsForValue().get("future1"));
        } else {
            System.out.println("not exits");
        }
    }

}
