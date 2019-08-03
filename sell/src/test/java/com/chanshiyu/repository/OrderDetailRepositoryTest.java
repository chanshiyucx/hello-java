package com.chanshiyu.repository;

import com.chanshiyu.dataobject.OrderDetail;
import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {


    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    private final String orderId = "1564800363505463397";

    private final String productId = "1564736031032204675";

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.genUniqueKey());
        orderDetail.setOrderId(orderId);
        ProductInfo productInfo = productInfoRepository.findById(productId).orElse(null);
        if (productInfo != null) {
            orderDetail.setProductId(productInfo.getProductId());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductQuantity(1);
        }
        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = orderDetailRepository.findByOrderId(orderId);
        Assert.assertNotEquals(0, result.size());
    }
}