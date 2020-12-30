package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author GYL
 * @time 2020/12/22-21:07
 */
@RequestMapping("category")
@Controller
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @ResponseBody
    @RequestMapping("queryCategoryPage")
    public HashMap<String, Object> queryCategoryPage(Integer page, Integer rows){
        return categoryService.queryCategoryPage(page, rows);
    }

    @ResponseBody
    @RequestMapping("queryTwoCategory")
    public HashMap<String, Object> queryTwoCategory(Integer page, Integer rows,String categoryId){
        return categoryService.queryCategoryTwoPage(page, rows,categoryId);
    }

    @ResponseBody
    @RequestMapping("edit")
    public void edit(Category category, String oper,String categoryId){
        if(oper.equals("add")){
           categoryService.add(category,categoryId);
        }
        if(oper.equals("edit")){
            categoryService.edit(category);
        }
        if(oper.equals("del")){
            categoryService.del(category,categoryId);
        }
    }
}
