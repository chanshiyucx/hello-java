package com.chanshiyu.dataobject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 商品类目
 */
@Entity // 实体
@DynamicUpdate // 自动更新时间
@Data // lombok 自动生成 getter 和 setter
@ApiModel("商品分类")
//@Table(name = "s_product_category") // 在表名和类目不一致时设置
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
    @ApiModelProperty("类目ID")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("类目编号")
    private Integer categoryType;

    /* 列表查询时需要增加一个默认的构造器 */
    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
