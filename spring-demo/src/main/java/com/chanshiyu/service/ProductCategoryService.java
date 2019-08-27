package com.chanshiyu.service;

import com.chanshiyu.dataobject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {

    /** 查询单个类目 */
    ProductCategory findOne(Integer id);

    /** 查询商品类目列表 */
    Page<ProductCategory> findList(Pageable pageable);

    /** 保存类目 */
    ProductCategory save(ProductCategory productCategory);

    /** 移除类目 */
    void delete(Integer id);

}
