package com.chanshiyu.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情
 */
@Entity
@DynamicUpdate
@Data
public class OrderDetail {

    @ApiModelProperty("详情ID")
    @Id
    private String detailId;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("商品ID")
    private String productId;

    @ApiModelProperty("商品名字")
    private String productName;

    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    @ApiModelProperty("商品数量")
    private Integer productQuantity;

    @ApiModelProperty("商品小图")
    private String productIcon;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
