package com.baizhi.zw.service;

import com.baizhi.zw.dao.GuanZhuDao;
import com.baizhi.zw.dao.UserDao;
import com.baizhi.zw.entity.City;
import com.baizhi.zw.entity.Guanzhu;
import com.baizhi.zw.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    GuanZhuDao guanZhuDao;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    //后台分页展示所有用户信息
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //分页处理
        int records = userDao.selectCount(null);
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows)));
        hashMap.put("status","200");
        return hashMap;
    }

    @Override
    //前台注册用户
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    //后台修改用户状态
    public void updateUser(String id) {
        User user = new User();
        user.setId(id);
        User user1 = userDao.selectByPrimaryKey(user);
        if ("1".equals(user1.getStatus())) {
            user1.setStatus("2");
        } else {
            user1.setStatus("1");
        }
        userDao.updateByPrimaryKeySelective(user1);
    }

    @Override
    //前台登陆修改最后登录时间
    public User updateUserByTime(User user) {
        userDao.updateByPrimaryKey(user);
        return userDao.selectByPrimaryKey(user);
    }

    @Override
    //后台根据注册时间统计
    public Map queryUserByTime() {
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.selectUserByTime("0", 1));
        manList.add(userDao.selectUserByTime("0", 7));
        manList.add(userDao.selectUserByTime("0", 30));
        manList.add(userDao.selectUserByTime("0", 365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.selectUserByTime("1", 1));
        womenList.add(userDao.selectUserByTime("1", 7));
        womenList.add(userDao.selectUserByTime("1", 30));
        womenList.add(userDao.selectUserByTime("1", 365));
        hashMap.put("man", manList);
        hashMap.put("women", womenList);
        return hashMap;
    }

    @Override
    //后台根据性别与地区分布展示
    public Map showUserMap() {
        HashMap hashMap = new HashMap();
        List<City> man = userDao.selectUserByLocation("0");
        List<City> women = userDao.selectUserByLocation("1");
        hashMap.put("man",man);
        hashMap.put("women",women);
        return hashMap;
    }

    @Override
    //前台处理登录请求判断用户名密码
    public User queryByPhoneAndPassword(String phone, String password) {
        return userDao.selectByPhoneAndPassword(phone,password);
    }

    @Override
    //前台修改用户信息
    public Map updateUserMsg(User user) {
        HashMap hashMap = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        hashMap.put("user",user);
        hashMap.put("status","200");
        return hashMap;
    }
    @Override
    //前台展示随机5位用户
    public Map goldGang(String uid) {
        HashMap hashMap = new HashMap();
        List<User> users = userDao.selectByRowBounds(null, new RowBounds(0, 5));
        hashMap.put("users",users);
        return hashMap;
    }

    @Override
    //前台添加关注
    public Map addGuanZhu(String uid, String id) {
        HashMap hashMap = new HashMap();
        Guanzhu guanzhu = new Guanzhu();
        guanzhu.setId(UUID.randomUUID().toString());
        guanzhu.setUserId(uid);
        guanzhu.setGuruId(id);
        guanZhuDao.insert(guanzhu);
        User user = new User();
        user.setId(uid);
        User user1 = userDao.selectByPrimaryKey(user);
        hashMap.put("status","200");
        hashMap.put("user",user1);
        return hashMap;
    }
}
