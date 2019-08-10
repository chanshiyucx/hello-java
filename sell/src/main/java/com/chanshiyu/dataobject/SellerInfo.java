package com.chanshiyu.dataobject;

import com.chanshiyu.enums.SellerStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息
 */
@Entity
@Data
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

    private String avatar;

    private Integer status = SellerStatusEnum.ACTIVE.getCode();
}
