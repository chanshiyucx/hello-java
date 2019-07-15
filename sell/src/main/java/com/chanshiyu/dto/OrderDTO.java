package com.chanshiyu.dto;

import com.chanshiyu.dataobject.OrderDetail;
import com.chanshiyu.enums.OrderStatusEnum;
import com.chanshiyu.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//DTO：data transform object 在各个层之间传输数据，类似于 DAO dataobject，在 DAO 基础上增加字段

@Data
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    // tintint 对应 Integer，给状态加上默认值
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 这里会用到创建和更新时间
    private Date createTime;

    private Date UpdateTime;

    private List<OrderDetail> orderDetailList;

}
