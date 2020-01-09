package com.baizhi.zw.aspect;

import com.baizhi.zw.util.ApplicationContentUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MyBatisCatche implements Cache {
    private final String id;

    public MyBatisCatche(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
    //添加缓存
    @Override
    public void putObject(Object key, Object value) {
        //通过
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContentUtils.getBeanByName("redisTemplate");
        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }
    //取缓存
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContentUtils.getBeanByName("redisTemplate");
        return redisTemplate.opsForHash().get(this.id, key.toString());
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }
    //删除缓存
    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContentUtils.getBeanByName("redisTemplate");
        redisTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
