package com.baizhi.serviceImpl;

import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.service.CategoryService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author GYL
 * @time 2020/12/22-20:57
 */

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public HashMap<String, Object> queryCategoryPage(Integer page, Integer rows) {
        // Integer page, Integer rows(每页展示条数)
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //创建条件对象
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        //查询总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }

    @Override
    public HashMap<String, Object> queryCategoryTwoPage(Integer page, Integer rows,String categoryId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(categoryId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }

    @Override
    public void add(Category category,String categoryId) {

        category.setId(UUIDUtil.getUUID());
        if(categoryId == null){
            category.setLevels(1);
            categoryMapper.insertSelective(category);
        }else {
            category.setLevels(2);
            category.setParentId(categoryId);
            System.out.println(category);
            categoryMapper.insertSelective(category);
        }
    }

    @Override
    public void edit(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void del(Category category,String categoryId) {
        categoryMapper.deleteByPrimaryKey(category);
    }
}
