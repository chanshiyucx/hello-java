package com.chanshiyu.service;

import com.chanshiyu.dataobject.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    /** 查询单个类目 */
    Category findOne(Integer id);

    /** 查询商品类目列表 */
    Page<Category> findList(Pageable pageable);

    /** 保存类目 */
    Category save(Category category);

    /** 移除类目 */
    void delete(Integer id);

}
