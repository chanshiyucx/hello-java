package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 类目名称
     */
    @Column(name = "category_name")
    @NotBlank(message = "类目名称不能为空")
    private String categoryName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}