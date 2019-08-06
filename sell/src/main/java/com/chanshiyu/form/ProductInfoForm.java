package com.chanshiyu.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品详情表单
 */
@Data
public class ProductInfoForm {

    @ApiModelProperty("商品ID")
    private String productId;

    @ApiModelProperty("商品名称")
    @NotEmpty(message = "商品名称必填")
    private String productName;

    @ApiModelProperty("商品价格")
    @NotNull(message = "商品价格必填")
    private BigDecimal productPrice;

    @ApiModelProperty("商品库存")
    @NotNull(message = "商品库存必填")
    private Integer productStock;

    @ApiModelProperty("商品状态")
    @NotNull(message = "商品状态必填")
    private Integer productStatus;

    @ApiModelProperty("商品描述")
    @NotEmpty(message = "商品描述必填")
    private String productDescription;

    @ApiModelProperty("商品小图")
    @NotEmpty(message = "商品小图必填")
    private String productIcon;

    @ApiModelProperty("商品类目")
    @NotNull(message = "商品类目必填")
    private Integer categoryType;
}
