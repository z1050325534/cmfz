package com.baizhi.zw.dao;

import com.baizhi.zw.entity.City;
import com.baizhi.zw.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    Integer selectUserByTime(@Param("sex")String sex,@Param("day")Integer day);
    List<City> selectUserByLocation(@Param("sex")String sex);
    User selectByPhoneAndPassword(@Param("phone") String phone,@Param("password") String password);
}
