package com.chanshiyu.service;

import com.chanshiyu.dto.OrderDTO;

public interface BuyerService {

    /** 查询单个订单 */
    OrderDTO findOrderOne(String openid, String orderId);

    /** 取消订单 */
    OrderDTO cancelOrder(String openid, String orderId);
}
