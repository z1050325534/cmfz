package com.baizhi.zw.service;

import com.baizhi.zw.entity.Admin;

import java.util.Map;

public interface AdminService {
    //验证后台用户名密码
    boolean loginAdmin(Admin admin);
}
