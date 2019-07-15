package com.chanshiyu.repository;

import com.chanshiyu.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    //@Transactional // 测试数据不插入数据库
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345678");
        orderMaster.setBuyerName("shiyu");
        orderMaster.setBuyerPhone("0123456789");
        orderMaster.setBuyerAddress("world");
        orderMaster.setBuyerOpenid("220220");
        orderMaster.setOrderAmount(new BigDecimal(1.1));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenidTest() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> result = repository.findByBuyerOpenid("110110", pageRequest);
        log.info(String.valueOf(result.getTotalElements()));
    }
}