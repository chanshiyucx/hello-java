package com.chanshiyu.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 卖家登录表单
 */
@Data
public class SellerLoginForm {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名必填")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码必填")
    private String password;
}
