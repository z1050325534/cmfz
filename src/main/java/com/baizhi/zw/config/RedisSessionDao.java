package com.baizhi.zw.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
public class RedisSessionDao extends AbstractSessionDAO{
    private long expireTime = 1800;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    // 创建Session
    protected Serializable doCreate(Session session) {
        // 获取SessionId 作为Key
        Serializable sessionId = this.generateSessionId(session);
        // 配对
        this.assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        return sessionId;
    }
    @Override
    // 取Session
    protected Session doReadSession(Serializable sessionId) {
        return sessionId == null ? null : (Session) redisTemplate.opsForValue().get(sessionId);
    }
    @Override
    // 更新Session信息
    public void update(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            session.setTimeout(expireTime * 1000);
            redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        }
    }
    @Override
    // 删除Session信息
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            redisTemplate.opsForValue().getOperations().delete(session.getId());
        }
    }
    @Override
    // 获取活跃的Session信息
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }
}
