package com.chanshiyu.service;

import com.chanshiyu.pojo.ProductCategory;
import com.chanshiyu.util.CommListResult;

public interface CategoryService {

    CommListResult<ProductCategory> list(int pageNum, int pageSize);

    ProductCategory create(ProductCategory bean) throws Exception;

    ProductCategory update(ProductCategory bean) throws Exception;

    void delete(int id) throws Exception;
}
