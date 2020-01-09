package com.baizhi.zw.service;

import com.baizhi.zw.dao.CounterDao;
import com.baizhi.zw.entity.Chapter;
import com.baizhi.zw.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;
    @Override
    //根据功课id展示所有计数器信息(功课id)
    public Map queryByCourseId(String uid, String id) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("courseId", id);
        List<Counter> counters = counterDao.selectByExample(e);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }

    @Override
    //添加计数器（计数器title，功课id）
    public Map insertCounter(String uid, String title,String courseId) {
        Counter counter = new Counter();
        counter.setId(UUID.randomUUID().toString());
        counter.setUserId(uid);
        counter.setTitle(title);
        counter.setCreateDate(new Date());
        counter.setCount(0);
        counter.setCourseId(courseId);
        counterDao.insert(counter);
        return queryByCourseId(uid,counter.getId());
    }


    @Override
    //删除计数器（计数器id）
    public Map deleteCounter(String uid, String id) {
        Counter counter = new Counter();
        counter.setId(id);
        counterDao.deleteByPrimaryKey(counter);
        //根据id查以一个-----功课id
        Counter counter1 = counterDao.selectByPrimaryKey(counter);
        return queryByCourseId(uid, counter1.getCourseId());
    }
    @Override
    //表更计数器
    public Map updateCounter(String uid, String id, Integer count) {
        Counter counter = new Counter();
        counter.setId(id);
        counter.setCount(count);
        counterDao.updateByPrimaryKeySelective(counter);
        //根据id查以一个-----功课id
        Counter counter1 = counterDao.selectByPrimaryKey(counter);
        return queryByCourseId(uid, counter1.getCourseId());
    }
}
