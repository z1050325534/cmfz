package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Admin;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface AdminDao extends Mapper<Admin>, DeleteByPrimaryKeyMapper<Admin>, InsertListMapper<Admin> {
    Admin queryAdminInfo(String principal);
    List<Admin> queryAdmin(@Param("offset")Integer offset,@Param("rows")Integer rows);
}