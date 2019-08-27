package com.chanshiyu.exception;

import com.chanshiyu.enums.ResultEnum;
import lombok.Getter;

/**
 * @author chanshiyu
 * @date 2019/8/27 15:21
 * @description 自定义异常
 */
@Getter
public class CommException extends RuntimeException {

    private Integer code;

    public CommException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CommException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
