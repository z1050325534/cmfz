package com.baizhi.zw.dao;

import com.baizhi.zw.aspect.MyBatisCatche;
import com.baizhi.zw.entity.Guru;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@CacheNamespace(implementation = MyBatisCatche.class)
public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
    List<Guru> selectByUser(@Param("uid") String uid);
}
