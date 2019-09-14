package com.chanshiyu.service;

import com.chanshiyu.pojo.ChatMsg;
import com.chanshiyu.util.CommListResult;

import java.util.List;

public interface ChatMsgService {

    /** 插入历史消息 */
    int createChatMsg(ChatMsg chatMsg);

    /** 获取历史消息 */
    CommListResult<ChatMsg> queryChatMsgList(String roomId, Integer lastMsgId, int pageNum, int pageSize);

}
