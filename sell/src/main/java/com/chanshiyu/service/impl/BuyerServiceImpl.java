package com.chanshiyu.service.impl;

import com.chanshiyu.dto.OrderDTO;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.service.BuyerService;
import com.chanshiyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    private OrderService orderService;

    @Autowired
    public BuyerServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) {
            return null;
        }

        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】订单的 openid 不一致，openid={}", openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner( openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查询不到该订单，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
}
