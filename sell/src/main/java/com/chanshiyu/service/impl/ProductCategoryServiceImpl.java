package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.repository.ProductCategoryRepository;
import com.chanshiyu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private ProductCategoryRepository repository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductCategory findOne(Integer categoryTypeId) {
        return repository.findById(categoryTypeId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findAllById(List<Integer> categoryIdList) {
        return repository.findAllById(categoryIdList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public void delete(Integer categoryTypeId) {
        repository.deleteById(categoryTypeId);
    }
}
