package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "friends_request")
public class FriendsRequest {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 发送者
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 接收者
     */
    @Column(name = "accept_user_id")
    private String acceptUserId;

    /**
     * 发送时间
     */
    @Column(name = "request_date_time")
    private Date requestDateTime;
}