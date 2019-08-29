package com.chanshiyu.exception;

import lombok.Getter;

/**
 * @author chanshiyu
 * @date 2019/8/29 10:16
 * @description 业务异常
 */
@Getter
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(ApiEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}