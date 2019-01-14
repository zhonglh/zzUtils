package com.zz.bms.util.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于redis 的分布式事务锁
 * @author Administrator
 */
public class RedissonManager {


    private String host  ;

    private String port  ;

    private String password;


    private Config config = new Config();
    private Redisson redisson = null;
    private Map<String,RLock> map = new HashMap<String,RLock>();


    public  void init(){
        try {
            config.useSingleServer().setConnectionPoolSize(1000).setAddress(host+":"+port);
            if(password != null && !password.trim().isEmpty()){
                config.useSingleServer().setPassword(password);
            }
            redisson = (Redisson)Redisson.create(config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void lock(String key){
        if(redisson == null){
            init();
        }

        RLock lock = redisson.getLock(key);
        lock.lock();
        map.put(key,lock);
    }

    public void unlock(String key){
        try {
            map.get(key).unlock();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public static void main(String[] args) {
        RedissonManager redissonManager = new RedissonManager();
        redissonManager.setHost("127.0.0.1");
        redissonManager.setPort("6379");
        redissonManager.init();
        String key = new String("");

        try {
            redissonManager.lock(key);
            //do business

        }finally {
            redissonManager.unlock(key);
        }
    }
}
