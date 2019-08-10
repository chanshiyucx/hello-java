package com.chanshiyu.enums;

import lombok.Getter;

@Getter
public enum SellerStatusEnum {

    ACTIVE(0, "正常"),
    BLOCK(1, "封禁")
    ;

    private Integer code;

    private String message;

    SellerStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
