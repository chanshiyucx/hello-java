package com.chanshiyu.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 下单表单
 */
@Data
public class OrderForm {

    @ApiModelProperty("买家姓名")
    @NotEmpty(message = "姓名必填")
    private String name;

    @ApiModelProperty("买家电话")
    @NotEmpty(message = "手机号必填")
    private String phone;

    @ApiModelProperty("买家地址")
    @NotEmpty(message = "地址必填")
    private String address;

    @ApiModelProperty("买家openid")
    @NotEmpty(message = "openid必填")
    private String openid;

    @ApiModelProperty("购买商品")
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
