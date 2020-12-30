package com.baizhi.dao;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {

    //后台管理员登录查询用户数据
    public Admin queryByUsername(String usename);
}