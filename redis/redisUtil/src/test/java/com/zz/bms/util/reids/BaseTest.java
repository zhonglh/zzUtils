package com.zz.bms.util.reids;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {
        "classpath:spring-util-context.xml",
        "classpath:spring-util-redis.xml"
})
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

}
