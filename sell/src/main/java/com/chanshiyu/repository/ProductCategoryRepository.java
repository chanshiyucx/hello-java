package com.chanshiyu.repository;

import com.chanshiyu.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /* 类似于 includes，根据 type 查列表 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
