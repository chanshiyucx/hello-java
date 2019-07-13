package com.chanshiyu.VO;

import lombok.Data;

/**
 * http 请求返回
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
