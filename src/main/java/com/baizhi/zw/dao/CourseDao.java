package com.baizhi.zw.dao;

import com.baizhi.zw.aspect.MyBatisCatche;
import com.baizhi.zw.entity.Course;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
@CacheNamespace(implementation = MyBatisCatche.class)
public interface CourseDao extends Mapper<Course> {
}
