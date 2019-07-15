package com.chanshiyu.exception;

import com.chanshiyu.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        // 调用父类的构造方法，传入 message
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
