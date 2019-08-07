package com.chanshiyu.dto;

import com.chanshiyu.dataobject.OrderDetail;
import com.chanshiyu.enums.OrderStatusEnum;
import com.chanshiyu.enums.PayStatusEnum;
import com.chanshiyu.utils.EnumUtil;
import com.chanshiyu.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("买家名称")
    private String buyerName;

    @ApiModelProperty("买家电话")
    private String buyerPhone;

    @ApiModelProperty("买家地址")
    private String buyerAddress;

    @ApiModelProperty("买家openid")
    private String buyerOpenid;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("商品列表")
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
