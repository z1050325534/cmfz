package com.baizhi.zw.controller;

import com.baizhi.zw.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    CounterService counterService;
    @RequestMapping("queryAllCounters")
    //前台展示所有计数器
    public Map queryAllCounters(String uid,String id){
        return counterService.queryByCourseId(uid,id);
    }
    @RequestMapping("insertCounter")
    //前台添加计数器
    public Map insertCounter(String uid,String title,String courseId){
        return counterService.insertCounter(uid,title,courseId);
    }
    @RequestMapping("deleteCounter")
    //删除计数器
    public Map deleteCounter(String uid,String id){
        return counterService.deleteCounter(uid,id);
    }
    @RequestMapping("updateCounter")
    public Map updateCounter(String uid,String id,Integer count){
        return counterService.updateCounter(uid,id,count);
    }
}
