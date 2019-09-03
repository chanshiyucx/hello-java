package com.chanshiyu.dto.category;

import com.chanshiyu.pojo.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/3 13:57
 * @description 商品类目详情
 */
@Data
@ApiModel(value = "商品类目详情")
public class CategoryDetail {

    @ApiModelProperty(value = "类目ID")
    private Integer id;

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "商品列表")
    private List<Product> productList;

}
