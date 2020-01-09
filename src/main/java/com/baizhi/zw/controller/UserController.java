package com.baizhi.zw.controller;

import com.baizhi.zw.entity.User;
import com.baizhi.zw.service.LogService;
import com.baizhi.zw.service.UserService;
import com.baizhi.zw.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    LogService logService;
    @Autowired
    RedisTemplate redisTemplate;

    //后台查询用户并展示
    @RequestMapping("queryUserByPage")
    public Map queryUserByPage(Integer page, Integer rows) {
        return userService.queryByPage(page, rows);
    }

    //后台修改用户状态
    @RequestMapping("updateUser")
    public String updateUser(String id) {
        userService.updateUser(id);
        return "ok";
    }

    /*
     * 导出excel表格
     * */
    @RequestMapping("moveShuJu")
    public Map moveShuJu() {
        HashMap hashMap = new HashMap();
        try {
            logService.exportUser();
            hashMap.put("message", "导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("message", "导出成功");
        }
        return hashMap;
    }

    /*
     * 导入excel表格
     * */
    @RequestMapping("addShuJu")
    public Map addShuJu(MultipartFile log) {
        HashMap hashMap = new HashMap();
        try {
            logService.addExportUser(log);
            hashMap.put("message", "导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("message", "导入成功");
        }
        return hashMap;
    }

    /*
     * 下载excel表格模板
     * */
    //后台展示所有用户注册事件
    @RequestMapping("showUserTime")
    public Map showUserTime() {
        return userService.queryUserByTime();
    }

    //后台展示用户地区统计
    @RequestMapping("selectBySexAndLocation")
    public Map selectBySexAndLocation() {
        return userService.showUserMap();
    }

    //前台登陆
    @RequestMapping("login")
    public Map login(String phone, String password) {
        HashMap hashMap = new HashMap();
        User user = userService.queryByPhoneAndPassword(phone, password);
        if (user != null) {
            user.setLastLogin(new Date());
            User user1 = userService.updateUserByTime(user);
            hashMap.put("status", "200");
            hashMap.put("user", user1);
        } else {
            hashMap.put("status", "-200");
            String message = "用户名或密码错误";
            hashMap.put("message", message);
        }
        return hashMap;
    }

    //发送验证码+返回msg及验证码（成功）/返回msg（失败）
    @RequestMapping("sendCode")
    public Map sendCode(String phone, HttpSession session) {
        //获取4位验证码随机数
        String random = PhoneUtil.getCode(4);
        session.setAttribute("random", random);
        redisTemplate.opsForValue().set("phone_" + phone, random,60, TimeUnit.SECONDS);
        //成功  ：status ，message , random (存redis并设置存活时间) | 失败：status ，message
        return PhoneUtil.getPhone(phone, random);
    }

    //注册接口   验证验证码
    @RequestMapping("insertTest")
    public Map insertTest(String code, HttpSession session) {
        HashMap hashMap = new HashMap();
        String random = (String) session.getAttribute("random");
        if (code.equals(random)) {
            hashMap.put("status", "200");
            hashMap.put("message", "验证成功");
        } else {
            hashMap.put("status", "-200");
            hashMap.put("message", "验证失败");
        }
        return hashMap;
    }

    //前台补全个人信息
    @RequestMapping("addUser")
    public Map addUser(String code, User user) {
        HashMap hashMap = new HashMap();
        user.setId(UUID.randomUUID().toString());
        user.setStatus("0");
        user.setRigestDate(new Date());
        user.setLastLogin(new Date());
        try {
            userService.insertUser(user);
            hashMap.put("user", user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            hashMap.put("message", "个人信息补充失败");
            hashMap.put("status", "-200");
        }

        return hashMap;
    }

    //添加关注上师
    @RequestMapping("addGuanZhu")
    public Map addGuanZhu(String uid, String id) {
        return userService.addGuanZhu(uid, id);
    }

    @RequestMapping("updateUserMsg")
    //前台修改用户信息
    public Map updateUserMsg(User user) {
        return userService.updateUserMsg(user);
    }
}
