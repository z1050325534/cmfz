package com.baizhi.zw.dao;

import com.baizhi.zw.aspect.MyBatisCatche;
import com.baizhi.zw.entity.Article;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
@CacheNamespace(implementation = MyBatisCatche.class)
public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
}
