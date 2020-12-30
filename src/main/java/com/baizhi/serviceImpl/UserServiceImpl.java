package com.baizhi.serviceImpl;

import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import com.baizhi.entity.UserPO;
import com.baizhi.service.Userservice;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author GYL
 * @time 2020/12/21-22:23
 */
@Service
@Transactional
public class UserServiceImpl implements Userservice {
    @Resource
    UserMapper userMapper;

    //查询用户信息以及  页数的计算
    @Override
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows) {
        // Integer page, Integer rows(每页展示条数)
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        UserExample example = new UserExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        //遍历集合
        for (User user : users) {
            //根据用户id查询学分  redis
            String id = user.getId();
            //查询学分并赋值
            user.setScore("88");
        }

        map.put("rows",users);

        //查询总条数
        int records = userMapper.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }

    //用户添加
    @Override
    public String add(User user) {

        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setCreatDate(new Date());
        user.setStatus("1");
        userMapper.insertSelective(user);

        //添加方法返回id
        return uuid;
    }

    //文件上传
    @Override
    public void uploadUserCover(MultipartFile headImg, String id, HttpServletRequest request) {
        //1.获取文件名
        String filename = headImg.getOriginalFilename();
        //图片拼接时间戳
        String newName=new Date().getTime()+"-"+filename;

        //2.根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/cover");

        //获取文件夹
        File file = new File(realPath);
        //判断文件夹是否存在
        if(!file.exists()){
            file.mkdirs();//创建文件夹
        }

        //3.文件上传
        try {
            headImg.transferTo(new File(realPath,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("newName"+newName);

        User user = new User();
        user.setId(id);
        user.setHeadImg(newName);

        //4.修改数据
        userMapper.updateByPrimaryKeySelective(user);
    }

    //修改信息
    @Override
    public void edit(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    //用户删除
    @Override
    public void del(User user) {
        userMapper.deleteByPrimaryKey(user);
    }

    @Override
    public HashMap<String, Object> queryAll() {
        HashMap<String, Object> map = new HashMap<>();
        UserExample example = new UserExample();
        List<User> manuser = userMapper.queryAllMan();
        map.put("manuser",manuser);
        List<User> womanuser = userMapper.queryAllWoman();
        map.put("womanuser",womanuser);
        return map;
    }

    @Override
    public List<UserPO> queryAlls() {
        UserExample example = new UserExample();
        List<UserPO> userPOS = userMapper.queryAll();


        return userPOS;
    }
}
