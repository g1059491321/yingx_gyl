package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.Userservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author GYL
 * @time 2020/12/22-9:48
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    Userservice userservice;

    //用户信息的遍历以及页数的展示
    @ResponseBody
    @RequestMapping("queryUserPage")
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows){
        return userservice.queryUserPage(page, rows);
    }

    //用户信息的增删改
    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user, String oper){
        String id =null;
        if(oper.equals("add")){
            id = userservice.add(user);
        }
        if(oper.equals("edit")){
            userservice.edit(user);
        }
        if(oper.equals("del")){
            userservice.del(user);
        }
        return id;
    }

    @ResponseBody
    @RequestMapping("uploadUserCover")
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request){
        userservice.uploadUserCover(headImg, id, request);
    }

}
