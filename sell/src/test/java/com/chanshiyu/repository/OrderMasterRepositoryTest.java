package com.chanshiyu.repository;

import com.chanshiyu.dataobject.OrderMaster;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110110";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.genUniqueKey());
        orderMaster.setBuyerName("shiyu");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("幻想乡");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(1000));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenidTest() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
    }
}