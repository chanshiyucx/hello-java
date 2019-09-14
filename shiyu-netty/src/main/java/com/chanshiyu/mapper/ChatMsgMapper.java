package com.chanshiyu.mapper;

import com.chanshiyu.pojo.ChatMsg;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

public interface ChatMsgMapper extends InsertUseGeneratedKeysMapper<ChatMsg> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
    int insertUseGeneratedKeys(ChatMsg chatMsg);

}