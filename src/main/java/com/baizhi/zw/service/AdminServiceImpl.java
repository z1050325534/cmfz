package com.baizhi.zw.service;

import com.baizhi.zw.dao.AdminDao;
import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.realm.MyRealm;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    //后台验证用户名密码
    public boolean loginAdmin(Admin admin) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return subject.isAuthenticated();
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //分页处理
        int records = adminDao.selectCount(null);
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        //hashMap.put("rows", adminDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows)));
        hashMap.put("rows", adminDao.queryAdmin( (page - 1) * rows,rows));
        return hashMap;
    }
}
