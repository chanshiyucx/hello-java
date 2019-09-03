package com.chanshiyu.mapper;

import com.chanshiyu.dto.category.CategoryDetail;
import com.chanshiyu.pojo.Category;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {
    CategoryDetail selectCategoryDetail(int categoryId);
}