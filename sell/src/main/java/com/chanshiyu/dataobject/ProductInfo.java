package com.chanshiyu.dataobject;

import com.chanshiyu.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品详情
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    /** 商品ID */
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品状态 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 商品描述 */
    private String productDescription;

    /** 商品小图 */
    private String productIcon;

    /** 商品类目 */
    private Integer categoryType;
}
