package com.chanshiyu.service;

import com.chanshiyu.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /** 查询单个类目 */
    ProductCategory findOne(Integer categoryTypeId);

    /** 查询所有类目列表 */
    List<ProductCategory> findAll();

    /** 查询指定类目列表 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /** 保存类目 */
    ProductCategory save(ProductCategory productCategory);
}
