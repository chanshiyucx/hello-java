package com.chanshiyu.repository;

import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

     @Test
    public void saveTest() {
         ProductInfo productInfo = new ProductInfo();
         productInfo.setProductId(KeyUtil.genUniqueKey());
         productInfo.setProductName("灵梦手办");
         productInfo.setProductPrice(new BigDecimal("800"));
         productInfo.setProductDescription("東方Project大手办");
         productInfo.setProductStock(100);
         productInfo.setProductStatus(0);
         productInfo.setProductIcon("https://chanshiyu.com");
         productInfo.setCategoryType(1);
         ProductInfo result = repository.save(productInfo);
         Assert.assertNotNull(result);
     }

     @Test
    public void findByProductStatus() {
         List<ProductInfo> result = repository.findByProductStatus(0);
         Assert.assertEquals(0, result.size());
     }
}