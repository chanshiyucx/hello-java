package com.chanshiyu.service.impl;

import com.chanshiyu.mapper.CategoryMapper;
import com.chanshiyu.pojo.Category;
import com.chanshiyu.service.CategoryService;
import com.chanshiyu.util.CommListResult;
import com.chanshiyu.util.JSONResultAttributes;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author chanshiyu
 * @date 2019/8/28 19:33
 * @description 商品类目Service
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public CommListResult<Category> list(int pageNum, int pageSize) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 获取列表
        Example example = new Example(Category.class);
        List<Category> vos = categoryMapper.selectByExample(example);
        // 用PageInfo对结果进行包装
        PageInfo info = new PageInfo<>(vos);
        JSONResultAttributes attributes = new JSONResultAttributes(info.getPageNum(), info.getPageSize(), info.getTotal());
        return new CommListResult<>(vos, attributes);
    }

    @Override
    @Transactional
    public Category create(Category bean) throws Exception {
        if (categoryMapper.insertSelective(bean) <= 0) {
            throw new Exception("创建失败");
        }
        return bean;
    }

    @Override
    @Transactional
    public Category update(Category bean) throws Exception {
        if (categoryMapper.updateByPrimaryKeySelective(bean) <= 0) {
            throw new Exception("更新失败");
        }
        return bean;
    }

    @Override
    @Transactional
    public void delete(int id) throws Exception {
        categoryMapper.deleteByPrimaryKey(id);
    }

}
