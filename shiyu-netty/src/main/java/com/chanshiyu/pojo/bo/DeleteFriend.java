package com.chanshiyu.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author shiyu
 * @date 2019/9/12 9:06
 * @Description: 删除好友
 */
@Data
public class DeleteFriend {

    @NotBlank(message = "用户ID不能为空")
    public String userId;

    @NotBlank(message = "好友用户ID不能为空")
    public String friendUserId;

}
