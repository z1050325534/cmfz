package com.baizhi.zw.service;

import com.baizhi.zw.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //前台展示所有文章信息
    Map queryByPage(Integer page, Integer rows);
    //修改文章信息
    void updateOne(Article article);
    //删除文章信息
    void delete(String id);
    //添加文章信息
    void insertOne(Article article);
    //查一个文章信息
    Article queryOne(String id);
    //前台展示
    List<Article> queryByUid(String guruId);
    //前台展示查所有
    List<Article> queryAll();
}
