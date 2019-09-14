package com.chanshiyu.pojo;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_msg")
public class ChatMsg {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 房间ID
     */
    @Column(name = "room_id")
    private String roomId;

    /**
     * 发送者ID
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 发送者昵称
     */
    @Column(name = "send_user_nickname")
    private String sendUserNickname;

    /**
     * 发送者头像
     */
    @Column(name = "send_user_avatar")
    private String sendUserAvatar;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}