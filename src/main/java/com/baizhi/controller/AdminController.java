package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author GYL
 * @time 2020/12/21-17:30
 */
@RequestMapping("admin")
@Controller
public class AdminController {

    @Resource
    AdminService adminService;

    @RequestMapping("getImageCode")
    public void getImageCode(HttpSession session, HttpServletResponse response){
        //1.获取随机字符
        String randomCode = ImageCodeUtil.getSecurityCode();
        System.out.println("验证码是:"+randomCode);
        //2.存储随机字符
        session.setAttribute("imageCode",randomCode);
        //3.根据随机字符生成验证码的图片
        BufferedImage image = ImageCodeUtil.createImage(randomCode);
        //4.将图片响应到页面
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("login")
    public HashMap<String, Object> login(Admin admin, String enCode){
        return adminService.login(admin,enCode);
    }

    //安全退出
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }

    //管理员信息的增删改
    @ResponseBody
    @RequestMapping("edit")
    public void edit(Admin admin, String oper){
        if(oper.equals("add")){
            adminService.add(admin);
        }
        if(oper.equals("edit")){
            adminService.edit(admin);
        }
        if(oper.equals("del")){
            adminService.del(admin);
        }
    }

    //页数的查询以及管理员信息的展示
    @ResponseBody
    @RequestMapping("queryAdminPage")
    public HashMap<String, Object> queryAdminPage(Integer page, Integer rows){
        return adminService.queryUserPage(page, rows);
    }
}
