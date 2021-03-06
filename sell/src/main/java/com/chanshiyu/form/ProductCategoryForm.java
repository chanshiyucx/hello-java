package com.chanshiyu.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 商品类目表单
 */
@Data
public class ProductCategoryForm {

    @ApiModelProperty("类目ID")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    @NotEmpty(message = "类目名称必填")
    private String categoryName;
}

