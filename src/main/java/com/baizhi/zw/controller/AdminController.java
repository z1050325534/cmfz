package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.service.AdminService;
import com.baizhi.zw.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    LogService logService;
    @RequestMapping("/login")
    //后台登陆
    public Map login(Admin admin, String code, HttpSession session){
        HashMap hashMap = new HashMap();
        //从session获取验证码
        String code1 = (String) session.getAttribute("code");
        //验证码判断
        if(code1.equals(code)){
            //判断用户名密码
            boolean b = adminService.loginAdmin(admin);
            if(b){
                hashMap.put("status","200");
                return hashMap;
            }else{
                hashMap.put("status","400");
                hashMap.put("msg","密码错误");
                return hashMap;
            }
        }
        hashMap.put("status","400");
        hashMap.put("msg","验证码错误");
        return hashMap;
    }
    //后台查询日志并分页
    @RequestMapping("/queryLogByPage")
    public Map queryLogByPage(Integer page,Integer rows){
        return logService.queryByPage(page,rows);
    }
}
