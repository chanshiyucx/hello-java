package com.chanshiyu.repository;

import com.chanshiyu.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanshiyu
 * @date 2019/8/27 14:16
 * @description
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {}
