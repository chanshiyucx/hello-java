package com.chanshiyu.vo;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chanshiyu
 * @date 2019/8/27 9:50
 * @description 自定义响应数据结构
 * 200：表示成功
 * 500：表示错误
 */
@Data
public class ResultVO<T> {

    /** 响应业务状态 */
    private Integer status;

    /** 响应消息 */
    private String msg;

    /** 响应中的数据 */
    private T data;

    /** 分页描述信息 */
    private ResultAttributesVO attributes;

    private ResultVO() {
        this.status = 200;
        this.msg = "OK";
    }

    private ResultVO(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    private ResultVO(T data, ResultAttributesVO attributes) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.attributes = attributes;
    }

    private ResultVO(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultVO<T> ok() { return new ResultVO<>(); }

    public static <T> ResultVO<T> ok(T data) {
        return new ResultVO<>(data);
    }

    public static <T> ResultVO<T> ok(T data, ResultAttributesVO attributes) {
        return new ResultVO<>(data, attributes);
    }

    public static <T> ResultVO<T> errorMsg(String msg) {
        return new ResultVO<>(500, msg, null);
    }

    public Boolean isOK() {
        return this.status >= 200 && this.status < 300;
    }

}
