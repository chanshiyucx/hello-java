package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOneTest() {
        ProductCategory result = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1), result.getCategoryId());
    }

    @Test
    public void findAllTest() {
        List<ProductCategory> result = productCategoryService.findAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllByIdTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<ProductCategory> result = productCategoryService.findAllById(list);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("畅销榜");
        ProductCategory result = productCategoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}