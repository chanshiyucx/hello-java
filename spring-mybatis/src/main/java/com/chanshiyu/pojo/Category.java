package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 类目名称
     */
    private String name;

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