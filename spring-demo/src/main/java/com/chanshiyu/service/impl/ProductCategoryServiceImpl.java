package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.repository.ProductCategoryRepository;
import com.chanshiyu.service.ProductCategoryService;
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
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductCategory> findList(Pageable pageable) {
        return productCategoryRepository.findAll(pageable);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void delete(Integer id) {
        productCategoryRepository.deleteById(id);
    }

}
