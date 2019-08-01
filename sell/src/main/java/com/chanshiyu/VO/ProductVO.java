package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品信息
 */
@Data
@ApiModel("商品类目详情")
public class ProductVO {

    @ApiModelProperty("类目名称")
    @JsonProperty("name")
    private String categoryName;

    @ApiModelProperty("类目类型")
    @JsonProperty("type")
    private Integer categoryType;

    @ApiModelProperty("商品列表")
    @JsonProperty("list")
    private List<ProductInfoVO> productInfoVOList;
}
