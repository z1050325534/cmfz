package com.baizhi.zw.dao;


import com.baizhi.zw.aspect.MyBatisCatche;
import com.baizhi.zw.entity.Guanxi;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
@CacheNamespace(implementation = MyBatisCatche.class)
public interface GuanXiDao extends Mapper<Guanxi> {
}
