package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOneTest() {
        ProductInfo result = productInfoService.findOne("001");
        Assert.assertNotNull(result);
    }

    @Test
    public void findUpAllTest() {
        List<ProductInfo> result = productInfoService.findUpAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllTest() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> result = productInfoService.findAll(pageRequest);
        log.info(String.valueOf(result.getTotalElements()));
    }

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("001");
        productInfo.setProductName("商品1");
        productInfo.setProductPrice(new BigDecimal(100));
        productInfo.setProductStock(10);
        productInfo.setProductStatus(0);
        productInfo.setProductDescription("hello chanshiyu");
        productInfo.setProductIcon("https://chanshiyu.com");
        productInfo.setCategoryType(2);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}