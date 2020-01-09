package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Guru;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
    List<Guru> selectByUser(@Param("uid") String uid);
}
