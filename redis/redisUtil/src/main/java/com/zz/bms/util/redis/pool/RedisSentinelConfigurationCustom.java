package com.zz.bms.util.redis.pool;

import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/**
 * @author Administrator
 */
public class RedisSentinelConfigurationCustom extends RedisSentinelConfiguration {

    public RedisSentinelConfigurationCustom(String master) {
        super(master, SetSentinel.getSentinelNodes());
    }

}
