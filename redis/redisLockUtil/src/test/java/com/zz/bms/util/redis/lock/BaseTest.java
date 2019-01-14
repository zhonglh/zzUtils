package com.zz.bms.util.redis.lock;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
        "classpath:spring-util-context.xml",
        "classpath:spring-util-redis-lock.xml"
})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

}
