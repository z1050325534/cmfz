package com.baizhi.zw.realm;

import com.baizhi.zw.dao.AdminDao;
import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.util.ApplicationContentUtils;
import com.baizhi.zw.entity.Resource;
import com.baizhi.zw.entity.Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
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
        AuthenticationInfo authenticationInfo = new SimpleAccount(adminByDB.getUsername(),adminByDB.getPassword(), ByteSource.Util.bytes("abcd"),this.getName());
        return authenticationInfo;
    }

    @Override
    // doGetAuthorizationInfo 获取授权信息的方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取主身份信息
        String principal = (String) principalCollection.getPrimaryPrincipal();
        // 根据主身份信息 从数据库查询权限信息
        AdminDao adminDao = (AdminDao) ApplicationContentUtils.getBeanByClass(AdminDao.class);
        Admin admin = adminDao.queryAdminInfo(principal);
        System.out.println(admin);
        // 返回值信息 使用 SimpleAuthorizationInfo 类型 不要使用父类引用
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户信息的身份信息
        List<Role> roles = admin.getRoles();
        // 手动封装String类型集合
        ArrayList arrayList = new ArrayList();
        // 手动封装权限信息的String类型集合
        ArrayList arrayList2 = new ArrayList();
        for (Role role : roles) {
            arrayList.add(role.getRoleName());
            for (Resource resource : role.getResources()) {
                arrayList2.add(resource.getResourceName());
            }
        }
        simpleAuthorizationInfo.addRoles(arrayList);
        simpleAuthorizationInfo.addStringPermissions(arrayList2);
        return simpleAuthorizationInfo;
    }
}
