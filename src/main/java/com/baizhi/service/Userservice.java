package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserPO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author GYL
 * @time 2020/12/21-22:23
 */
public interface Userservice {
    public HashMap<String,Object> queryUserPage(Integer page, Integer rows);

    public String add(User user);

    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request);

    public void edit(User user);

    public void del(User user);

    public HashMap<String,Object> queryAll();

    public List<UserPO> queryAlls();
}
