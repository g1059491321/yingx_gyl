package com.baizhi.serviceImpl;

import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import com.baizhi.service.AdminService;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author AdminServiceImpl
 * @time 2020/12/19--22:17
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    HttpSession session;

    @Resource
    AdminMapper adminMapper;

    @Override
    public HashMap<String, Object> login(Admin admin, String enCode) {
        //获取验证码
        String imageCode = (String) session.getAttribute("imageCode");

        HashMap<String,Object> map = new HashMap<>();
        //判断验证码
        if(enCode.equals(imageCode)){
            //根据用户名查询用户数据
            Admin admins = adminMapper.queryByUsername(admin.getUsername());
            //System.out.println(admins);
            if(admins != null){
                if (admins.getStatus().equals("1")){
                    String md5Code = Md5Utils.getMd5Code(admins.getSalt() + admin.getPassword() + admins.getSalt());
                    if(admin.getPassword().equals(md5Code)){
                        session.setAttribute("admin",admins);
                        map.put("status","200");
                        map.put("message","登录成功");
                    }else{
                        map.put("status","401");
                        map.put("message","密码不正确");
                    }
                }else{
                    map.put("status","401");
                    map.put("message","该用户已冻结");
                }
            }else{
                map.put("status","401");
                map.put("message","该用户不存在");
            }
        }else{
            map.put("status","401");
            map.put("message","您输入的验证码有误");
        }
        return map;
    }

    @Override
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows) {
        // Integer page, Integer rows(每页展示条数)
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        AdminExample example = new AdminExample();
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Admin> admins = adminMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",admins);

        //查询总条数
        int records = adminMapper.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;

    }

    @Override
    public void add(Admin admin) {
        //先对管理员的账号状态和ID进行修改
        admin.setId(UUIDUtil.getUUID());
        admin.setSalt("1");
        admin.setStatus("1");
        //生成随机盐
        String salt = Md5Utils.getSalt(4);
        //对密码进行加密
        String md5Code = Md5Utils.getMd5Code(salt + admin.getPassword() + salt);

        admin.setSalt(salt);
        admin.setPassword(md5Code);

        adminMapper.insertSelective(admin);
    }

    @Override
    public void edit(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void del(Admin admin) {
        adminMapper.deleteByPrimaryKey(admin);
    }
}
