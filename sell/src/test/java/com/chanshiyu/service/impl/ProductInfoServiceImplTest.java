package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.enums.ProductStatusEnum;
import com.chanshiyu.utils.KeyUtil;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOneTest() {
        ProductInfo result = productInfoService.findOne("1564736031032204675");
        Assert.assertNotNull(result);
    }

    @Test
    public void findUpAllTest() {
        List<ProductInfo> result = productInfoService.findUpAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllTest() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductInfo> result = productInfoService.findAll(pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
    }

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.genUniqueKey());
        productInfo.setProductName("求闻史记");
        productInfo.setProductPrice(new BigDecimal(50));
        productInfo.setProductStock(20);
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductDescription("東方求闻史记");
        productInfo.setProductIcon("https://chanshiyu.com");
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}