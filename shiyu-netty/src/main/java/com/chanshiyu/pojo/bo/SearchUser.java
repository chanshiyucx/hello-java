package com.chanshiyu.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author shiyu
 * @date 2019/9/11 15:08
 * @Description: 搜索好友
 */
@Data
public class SearchUser {

    @NotBlank(message = "用户ID不能为空")
    public String myUserId;

    @NotBlank(message = "搜索用户名不能为空")
    public String friendUserName;

}
