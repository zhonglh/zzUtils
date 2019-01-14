package com.zz.bms.util.redis.lock;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class RedissonTest extends BaseTest {


    @Resource(name="redissonManager")
    private RedissonManager redissonManager ;



    @Test
    public void testRedisLocak() {
        String key = new String("zz");

        try {
            redissonManager.lock(key);
            //do business

        }finally {
            redissonManager.unlock(key);
        }
    }

}
