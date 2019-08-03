package com.chanshiyu.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 订单详情
 */
@Entity
@DynamicUpdate
@Data
public class OrderDetail {

    /** 详情ID */
    @Id
    private String detailId;

    /** 订单ID */
    private String orderId;

    /** 商品ID */
    private String productId;

    /** 商品名字 */
    private String productName;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品小图 */
    private String productIcon;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
