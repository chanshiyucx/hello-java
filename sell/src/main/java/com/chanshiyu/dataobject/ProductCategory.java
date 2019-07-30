package com.chanshiyu.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 商品类目
 */
@Entity // 实体
@DynamicUpdate // 自动更新时间
@Data // lombok 自动生成 getter 和 setter
//@Table(name = "s_product_category") // 在表名和类目不一致时设置
public class ProductCategory {

    /* 类目 id, 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
    private Integer categoryId;

    /* 类目名称 */
    private String categoryName;

    /* 类目编号 */
    private Integer categoryType;

    /* 列表查询时需要增加一个默认的构造器 */
    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
