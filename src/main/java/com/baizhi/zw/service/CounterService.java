package com.baizhi.zw.service;

import java.util.Map;

public interface CounterService {
    //展示计数器
    Map queryByCourseId(String uid, String id);
    //添加计数器
    Map insertCounter(String uid,String title,String courseId);
    //删除计数器
    Map deleteCounter(String uid,String id);
    //表更计数器
    Map updateCounter(String uid,String id,Integer count);
}
