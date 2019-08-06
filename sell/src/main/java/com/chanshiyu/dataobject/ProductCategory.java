package com.chanshiyu.dataobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商品类目
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    @ApiModelProperty("类目ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;

    /* 列表查询时需要增加一个默认的构造器 */
    public ProductCategory() {}

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
