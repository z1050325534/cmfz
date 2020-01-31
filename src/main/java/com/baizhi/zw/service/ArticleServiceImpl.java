package com.baizhi.zw.service;

import com.baizhi.zw.dao.ArticleDao;
import com.baizhi.zw.annotation.LogAnnotation;
import com.baizhi.zw.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    @LogAnnotation(value = "查询文章信息")
    @Transactional(propagation = Propagation.SUPPORTS)
    //后台分页展示文章信息
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //分页处理
        int records = articleDao.selectCount(null);
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows)));
        return hashMap;
    }

    @Override
    @LogAnnotation(value = "修改文章信息")
    //修改文章信息
    public void updateOne(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @LogAnnotation(value = "删除文章信息")
    //根据id删除文章
    public void delete(String id) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String realPath1 = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/article/");
        Article article = new Article();
        article.setId(id);
        Article article1 = articleDao.selectByPrimaryKey(article);
        String filename = article1.getFilename();
        //删除数据库及上传的img
        File file = new File(realPath1, filename);
        if (file != null) {
            if (file.exists()) {
                boolean delete = file.delete();
                System.out.println(delete);
            }
        }
        articleDao.deleteByPrimaryKey(article);
    }

    @Override
    @LogAnnotation(value = "添加文章信息")
    //添加文章
    public void insertOne(Article article) {
        articleDao.insert(article);
    }

    @Override
    //查一个
    public Article queryOne(String id) {
        return articleDao.selectByPrimaryKey(id);
    }

    @Override
    //前台展示（根据上师id展示文章）
    public List<Article> queryByUid(String guruId) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("guru_id", guruId);
        return articleDao.selectByExample(example);
    }

    @Override
    //前台查所有
    public List<Article> queryAll() {
        return articleDao.selectAll();
    }
}
