package com.baizhi.zw.dao;

import com.baizhi.zw.aspect.MyBatisCatche;
import com.baizhi.zw.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@CacheNamespace(implementation = MyBatisCatche.class)
public interface BannerDao extends Mapper<Banner> , DeleteByIdListMapper<Banner,String>{
    List<Banner> selectBannersByTime();
}
