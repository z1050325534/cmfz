package com.baizhi.zw.service;

import com.baizhi.zw.dao.AdminDao;
import com.baizhi.zw.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    //后台验证用户名密码
    public boolean loginAdmin(Admin admin) {
        Admin admin1 = adminDao.selectOne(admin);
        if(admin1!=null){
                if(admin1.getPassword().equals(admin.getPassword())){
                    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    //验证成功 将用户名存到session
                    requestAttributes.getRequest().getSession().setAttribute("username",admin1.getUsername());
                    return true;
                }
        }
        //验证失败
        return false;
    }
}
