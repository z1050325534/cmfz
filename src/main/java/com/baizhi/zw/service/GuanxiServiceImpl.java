package com.baizhi.zw.service;

import com.baizhi.zw.dao.ArticleDao;
import com.baizhi.zw.dao.GuanXiDao;
import com.baizhi.zw.dao.GuruDao;
import com.baizhi.zw.entity.Article;
import com.baizhi.zw.entity.Guanxi;
import com.baizhi.zw.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
@Transactional
public class GuanxiServiceImpl implements GuanxiService {
    @Autowired
    GuanXiDao guanXiDao;
    @Autowired
    GuruDao guruDao;
    @Autowired
    ArticleDao articleDao;
    @Override
    //根据uid查询所有关注的上师的文章
    public List<Article> queryBy(String uid) {
        Example e = new Example(Guanxi.class);
        e.createCriteria().andEqualTo("uid", "1");
        List<Guanxi> guanxis = guanXiDao.selectByExample(e);
        return null;
    }
}
