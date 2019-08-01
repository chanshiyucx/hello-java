package com.chanshiyu.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存错误"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详细不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单状态更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单无商品详情"),
    PAY_STATUS_ERROR(17, "支付状态不正确"),
    PAY_STATUS_UPDATE_FAIL(18, "支付状态更新失败"),
    CART_EMPTY(19, "购物车不能为空"),
    ORDER_OWNER_ERROR(20, "该订单不属于当前用户")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}