package com.chanshiyu.exception;

import lombok.Getter;

/**
 * @author chanshiyu
 * @date 2019/8/29 10:55
 * @description 业务状态枚举
 */
@Getter
public enum ApiEnum {

    PARAM_ERROR(10, "参数不正确")
    ;

    private Integer code;

    private String message;

    ApiEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}