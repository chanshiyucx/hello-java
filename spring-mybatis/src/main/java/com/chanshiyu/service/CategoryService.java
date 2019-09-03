package com.chanshiyu.service;

import com.chanshiyu.dto.category.CategoryDetail;
import com.chanshiyu.pojo.Category;
import com.chanshiyu.util.CommListResult;

public interface CategoryService {

    CommListResult<Category> list(int pageNum, int pageSize);

    Category create(Category bean) throws Exception;

    Category update(Category bean) throws Exception;

    void delete(int categoryId) throws Exception;

    CategoryDetail detail(int categoryId);

}
