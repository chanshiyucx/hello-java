package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.Category;
import com.chanshiyu.repository.CategoryRepository;
import com.chanshiyu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author chanshiyu
 * @date 2019/8/27 14:17
 * @description 商品类目Service
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findOne(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Category> findList(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

}
