package com.baizhi.controller;

import com.baizhi.entity.UserPO;
import com.baizhi.service.Userservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GYL
 * @time 2020/12/28-21:15
 */
@RequestMapping("echarts")
@Controller
public class EchartsController {

    @Resource
    Userservice userservice;

    @ResponseBody
    @RequestMapping("getUserData")
    public Map<String, Object> queryAll(){
        List<UserPO> userPOS = userservice.queryAlls();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("months", Arrays.asList("一月份","二月份","三月份","四月份","五月份","六月份"));
        map.put("counts", Arrays.asList(5, 20, 36, 10, 10, 20));

        String[] months = {"一月份","二月份","三月份","四月份","五月份","六月份"};
        Integer[] count1 = {5, 20, 36, 10, 100, 20};
        Integer[] count2 = {5, 20, 36, 20, 50, 20};

        return map;
    }
}
