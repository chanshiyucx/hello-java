package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 房间名称
     */
    private String name;

    /**
     * 房间图标
     */
    private String icon;

    /**
     * 房间所有人
     */
    @NotBlank(message = "房间用户不能为空")
    private String users;

    /**
     * 创建者
     */
    @NotBlank(message = "创建者不能为空")
    @Column(name = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}