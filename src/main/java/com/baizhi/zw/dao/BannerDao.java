package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner> , DeleteByIdListMapper<Banner,String>{
    List<Banner> selectBannersByTime();
}
