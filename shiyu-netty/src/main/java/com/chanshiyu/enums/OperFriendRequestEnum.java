package com.chanshiyu.enums;

import lombok.Getter;

/**
 * @author shiyu
 * @date 2019/9/11 19:04
 * @Description: 申请验证操作枚举
 */
@Getter
public enum  OperFriendRequestEnum {

    IGNORE(0, "忽略"),
    PASS(1, "通过"),
    ;

    public final Integer status;

    public final String msg;

    OperFriendRequestEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgByKey(Integer status) {
        for (OperFriendRequestEnum type : OperFriendRequestEnum.values()) {
            if (status.equals(type.getStatus())) {
                return type.msg;
            }
        }
        return null;
    }


}
