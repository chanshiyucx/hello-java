package com.chanshiyu.enums;

import lombok.Getter;

@Getter
public enum ApiStatusEnums {

    SUCCESS(1, "成功"),
    PARAMS_ERROR(9, "参数错误"),
    FILE_NOT_EMPTY(10, "请上传文件"),
    USERNAME_PASSWORD_NOT_EMPTY(11, "用户名或密码不能为空"),
    FRIEND_NOT_NULL(12, "搜索好友不存在"),
    FRIEND_NOT_SELF(13, "不能添加自己为好友"),
    FRIEND_ON_LIST(14, "该用户已在列表中"),
    ;

    public final Integer status;

    public final String msg;

    ApiStatusEnums(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgByKey(Integer status) {
        for (ApiStatusEnums type : ApiStatusEnums.values()) {
            if (status.equals(type.getStatus())) {
                return type.msg;
            }
        }
        return null;
    }

}
