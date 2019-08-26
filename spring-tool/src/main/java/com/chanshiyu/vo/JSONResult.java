package com.chanshiyu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chanshiyu
 * @Description: 自定义响应数据结构
 * 200：表示成功
 * 500：表示错误
 */
@Data
@NoArgsConstructor
public class JSONResult<T> {

    /** 响应业务状态 */
    private Integer status;

    /** 响应消息 */
    private String msg;

    /** 响应中的数据 */
    private T data;

    /** 分页描述信息 */
    private JSONResultAttributes attributes;

    private JSONResult(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    private JSONResult(T data, JSONResultAttributes attributes) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.attributes = attributes;
    }

    private JSONResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JSONResult<T> ok(T data) {
        return new JSONResult<>(data);
    }

    public static <T> JSONResult<T> ok(T data, JSONResultAttributes attributes) {
        return new JSONResult<>(data, attributes);
    }

    public static <T> JSONResult<T> errorMsg(String msg) {
        return new JSONResult<>(500, msg, null);
    }

    public Boolean isOK() {
        return this.status >= 200 && this.status < 300;
    }

}
