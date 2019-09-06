package com.chanshiyu.pojo;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "my_friends")
public class MyFriends {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "my_user_id")
    private String myUserId;

    /**
     * 用户朋友ID
     */
    @Column(name = "my_friend_user_id")
    private String myFriendUserId;
}