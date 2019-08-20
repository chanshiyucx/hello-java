package com.chanshiyu.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 先创建实体类，再自动生成数据表
 */
@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private Integer age;
}
