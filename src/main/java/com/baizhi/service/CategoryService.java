package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.HashMap;

public interface CategoryService {

    //一级类别展示
    public HashMap<String,Object> queryCategoryPage(Integer page, Integer rows);

    //二级类别展示
    public HashMap<String,Object> queryCategoryTwoPage(Integer page, Integer rows,String categoryId);

    public void add(Category category,String categoryId);

    public void edit(Category category);

    public void del(Category category,String categoryId);
}
