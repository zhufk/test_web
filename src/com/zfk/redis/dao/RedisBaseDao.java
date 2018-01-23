package com.zfk.redis.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class RedisBaseDao<K extends Serializable, V extends Serializable> {

    @Resource
    protected RedisTemplate<K,V> redisTemplate;

    
      
    
}
