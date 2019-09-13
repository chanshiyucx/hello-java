package com.chanshiyu.netty.session;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shiyu
 * @date 2019/9/13 14:46
 * @Description
 */
@Data
@AllArgsConstructor
public class Session {

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

}
