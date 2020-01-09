package com.baizhi.zw.service;

import java.util.Map;


public interface CourseService {
    //根据用户id查功课
    Map queryByUser(String uid);
    //添加功课
    Map insertCourse(String uid,String title);
    //删除功课
    Map deleteCourse(String uid,String id);
}
