package com.chanshiyu.enums;

import lombok.Getter;

/**
 * @author chanshiyu
 * @date 2019/8/27 15:22
 * @description 请求状态枚举类
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1, "参数不正确"),
    CATEGORY_IS_EXIST(10, "商品类目已存在"),
    CATEGORY_NOT_EXIST(11, "商品类目已存在")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
