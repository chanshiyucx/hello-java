package com.chanshiyu.service.impl;

import com.chanshiyu.mapper.ChatMsgMapper;
import com.chanshiyu.pojo.ChatMsg;
import com.chanshiyu.service.ChatMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shiyu
 * @date 2019/9/14 10:05
 * @Description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatMsgServiceImpl implements ChatMsgService {

    private final ChatMsgMapper chatMsgMapper;

    @Override
    public int createChatMsg(ChatMsg chatMsg) {
        chatMsgMapper.insertUseGeneratedKeys(chatMsg);
        return chatMsg.getId();
    }
}
