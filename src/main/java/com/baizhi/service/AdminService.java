package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    public HashMap<String,Object> login(Admin admin,String enCode);

    public HashMap<String,Object> queryUserPage(Integer page, Integer rows);

    public void add(Admin admin);

    public void edit(Admin admin);

    public void del(Admin admin);

}
