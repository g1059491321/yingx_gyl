package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    public List<User> queryAllMan();

    public List<User> queryAllWoman();

    public List<UserPO> queryAll();
}