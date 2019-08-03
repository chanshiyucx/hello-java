package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 */
@Data
public class ProductInfoVO {

    @ApiModelProperty("商品ID")
    @JsonProperty("id")
    private String productId;

    @ApiModelProperty("商品名称")
    @JsonProperty("name")
    private String productName;

    @ApiModelProperty("商品价格")
    @JsonProperty("price")
    private BigDecimal productPrice;

    @ApiModelProperty("商品描述")
    @JsonProperty("description")
    private String productDescription;

    @ApiModelProperty("商品小图")
    @JsonProperty("icon")
    private String productIcon;
}
