package com.chanshiyu.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 头像大图
     */
    @Column(name = "avatar_big")
    private String avatarBig;

}