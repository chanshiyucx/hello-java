package com.chanshiyu.enums;

import lombok.Data;
import lombok.Getter;

// 注意要加上 Getter 注解
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    FINISHED(1, "已完成订单"),
    CANCEL(2, "已取消订单");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
