package com.baizhi.zw.controller;

import com.baizhi.zw.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;
    //前台展示功课
    @RequestMapping("/queryByUser")
    public Map queryByUser(String uid){
        return courseService.queryByUser(uid);
    }
    //添加功课
    @RequestMapping("addCourse")
    public Map addCourse(String uid,String title){
        return courseService.insertCourse(uid,title);
    }
    //删除功课
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,String id){
        return courseService.deleteCourse(uid,id);
    }
    //展示计数器
    @RequestMapping("queryCount")
    public Map queryCount(String uid,String id){
        return null;
    }
}
