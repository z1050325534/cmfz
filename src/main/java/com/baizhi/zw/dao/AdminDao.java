package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Admin;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface AdminDao extends Mapper<Admin>, DeleteByPrimaryKeyMapper<Admin>, InsertListMapper<Admin> {
}
