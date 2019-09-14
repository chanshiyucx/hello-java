package com.chanshiyu.service.impl;

import com.chanshiyu.mapper.ChatMsgMapper;
import com.chanshiyu.pojo.ChatMsg;
import com.chanshiyu.service.ChatMsgService;
import com.chanshiyu.util.CommListResult;
import com.chanshiyu.util.JSONResultAttributes;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public CommListResult<ChatMsg> queryChatMsgList(String roomId, Integer lastMsgId, int pageNum, int pageSize) {
        log.info("queryChatMsgList, {} - {} - {} - {}", roomId, lastMsgId, pageNum, pageSize);
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 获取列表
        List<ChatMsg> msgList = chatMsgMapper.queryChatMsgList(roomId, lastMsgId);
        //用PageInfo对结果进行包装
        PageInfo<ChatMsg> info = new PageInfo<>(msgList);
        JSONResultAttributes attributes = new JSONResultAttributes(info.getPageNum(), info.getPageSize(), info.getTotal());
        return new CommListResult<ChatMsg>(msgList, attributes);
    }
}
