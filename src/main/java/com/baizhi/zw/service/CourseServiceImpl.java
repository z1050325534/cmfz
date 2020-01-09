package com.baizhi.zw.service;

import com.baizhi.zw.dao.CounterDao;
import com.baizhi.zw.dao.CourseDao;
import com.baizhi.zw.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Autowired
    CounterDao counterDao;
    @Override
    //前台展示功课
    public Map queryByUser(String uid) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Course.class);
        e.createCriteria().andEqualTo("userId", uid);
        List<Course> courses = courseDao.selectByExample(e);
        hashMap.put("status","200");
        hashMap.put("courses",courses);
        return hashMap;
    }

    @Override
    //前台添加功课
    public Map insertCourse(String uid,String title) {
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setUserId(uid);
        course.setTitle(title);
        course.setType(null);
        course.setCreateDate(new Date());
        courseDao.insert(course);
        return queryByUser(uid);
    }

    @Override
    //删除功课
    public Map deleteCourse(String uid,String id) {
        Course course = new Course();
        course.setId(id);
        courseDao.delete(course);
        //删除子级所有计数器
        Example e = new Example(Course.class);
        e.createCriteria().andEqualTo("courseId", id);
        counterDao.deleteByExample(e);
        return queryByUser(uid);
    }

}
