package com.chanshiyu.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author shiyu
 * @date 2019/9/11 19:02
 * @Description: 申请验证
 */
@Data
public class OperFriendRequest {

    @NotBlank(message = "发送用户不能为空")
    public String sendUserId;

    @NotBlank(message = "接收用户不能为空")
    public String acceptUserId;

    /** 0忽略 1通过 */
    @NotNull(message = "操作类型不能为空")
    public Integer operType;

}
