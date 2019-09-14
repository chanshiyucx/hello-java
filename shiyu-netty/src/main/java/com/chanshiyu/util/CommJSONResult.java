package com.chanshiyu.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chanshiyu
 * @date 2019/8/29 9:17
 * @description 自定义响应数据结构
 * 200：表示成功
 * 500：表示错误
 */
@Data
@NoArgsConstructor
public class CommJSONResult<T> {

    /** 响应业务状态 */
    private Integer status;

    /** 响应消息 */
    private String msg;

    /** 响应中的数据 */
    private T data;

    /** 分页描述信息 */
    private JSONResultAttributes attributes;

    private CommJSONResult(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    private CommJSONResult(T data, JSONResultAttributes attributes) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.attributes = attributes;
    }

    private CommJSONResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommJSONResult<T> ok() {
        return new CommJSONResult<>(null);
    }

    public static <T> CommJSONResult<T> ok(T data) {
        return new CommJSONResult<>(data);
    }

    public static <T> CommJSONResult<T> ok(T data, JSONResultAttributes attributes) { return new CommJSONResult<>(data, attributes); }

    public static <T> CommJSONResult<T> errorMsg(String msg) {
        return new CommJSONResult<>(500, msg, null);
    }

    public Boolean isOK() {
        return this.status >= 200 && this.status < 300;
    }

}
