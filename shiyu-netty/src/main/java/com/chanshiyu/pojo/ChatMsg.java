package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "chat_msg")
public class ChatMsg {
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
     * 消息内容
     */
    private String msg;

    /**
     * 是否签收
     */
    @Column(name = "sign_flag")
    private Byte signFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}