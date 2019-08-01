package com.chanshiyu.VO;

import lombok.Data;

/**
 * Http 请求返回结果
 */
@Data
public class ResultVO<T> {

    private Integer status;

    private String msg;

    private T data;
}
