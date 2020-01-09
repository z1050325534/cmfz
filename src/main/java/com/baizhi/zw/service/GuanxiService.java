package com.baizhi.zw.service;

import com.baizhi.zw.entity.Article;

import java.util.List;

public interface GuanxiService {
    //根据uid查询所有关注的上师的文章
    List<Article> queryBy(String uid);
}
