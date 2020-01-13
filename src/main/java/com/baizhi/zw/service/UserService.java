package com.baizhi.zw.service;

import com.baizhi.zw.entity.User;

import java.util.Map;

public interface UserService {
    //后台分页展示所有用户信息
    Map queryByPage(Integer page, Integer rows);
    //后台修改用户状态
    void updateUser(String id);
    //后台展示所有用户注册时间分布
    Map queryUserByTime();
    //后台展示用户性别和月份查询地图分布
    Map showUserMap();
    //前台注册用户
    void insertUser(User user);
    //前台登陆
    User queryByPhoneAndPassword(String phone,String password);
    //前台登陆修改最后登录时间
    User updateUserByTime(User user);
    //前台修改用户信息
    Map updateUserMsg(User user);
    //金刚道友
    Map goldGang(String uid);
    //添加关注上师
    Map addGuanZhu(String uid,String id);
}
