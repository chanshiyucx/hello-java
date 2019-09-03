package com.chanshiyu.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品小图
     */
    private String icon;

    /**
     * 商品状态，0正常 1下架
     */
    private Integer status;

    /**
     * 类目编号
     */
    private Integer category;

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