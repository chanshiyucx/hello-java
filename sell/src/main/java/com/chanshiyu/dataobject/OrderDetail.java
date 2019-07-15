package com.chanshiyu.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private Integer productQuantity;

    private String productIcon;

    private Date createTime;

    private Date updateTime;
}
