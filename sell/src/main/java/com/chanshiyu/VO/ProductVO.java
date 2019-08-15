package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品信息
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -1127141450631719211L;

    @ApiModelProperty("类目名称")
    @JsonProperty("name")
    private String categoryName;

    @ApiModelProperty("商品列表")
    @JsonProperty("list")
    private List<ProductInfoVO> productInfoVOList;
}
