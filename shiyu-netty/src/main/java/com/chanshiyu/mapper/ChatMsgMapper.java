package com.chanshiyu.mapper;

import com.chanshiyu.pojo.ChatMsg;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

public interface ChatMsgMapper extends InsertUseGeneratedKeysMapper<ChatMsg> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
    int insertUseGeneratedKeys(ChatMsg chatMsg);

    /**
     * 根据时间查询历史聊天记录
     */
    List<ChatMsg> queryChatMsgList(@Param("roomId") String roomId, @Param("lastMsgId") Integer lastMsgId);

}