package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

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

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 设备ID
     */
    private String cid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}