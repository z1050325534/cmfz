package com.baizhi.zw.realm;

import com.baizhi.zw.dao.AdminDao;
import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.util.ApplicationContentUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        // 2. 根据用户名从数据库查询数据
        AdminDao adminDao = (AdminDao) ApplicationContentUtils.getBeanByClass(AdminDao.class);
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin adminByDB = adminDao.selectOne(admin);
        // 3. 封装AuthenticationInfo信息
        AuthenticationInfo authenticationInfo = new SimpleAccount(adminByDB.getUsername(),adminByDB.getPassword(), ByteSource.Util.bytes(adminByDB.getSalt()),this.getName());
        return authenticationInfo;
    }
}
