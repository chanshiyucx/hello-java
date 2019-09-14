package com.chanshiyu.controller;

import com.chanshiyu.pojo.ChatMsg;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.vo.UsersVO;
import com.chanshiyu.service.ChatMsgService;
import com.chanshiyu.util.CommJSONResult;
import com.chanshiyu.util.CommListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/14 15:23
 * @Description
 */
@RestController
@RequestMapping("/chat")
@Api(value = "消息管理", tags = {"消息管理Controller"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatMsgController {

    private final ChatMsgService chatMsgService;

    @ApiOperation(value = "历史消息", notes = "历史消息")
    @GetMapping("/list")
    public CommJSONResult<List<ChatMsg>> detail(
            @ApiParam(value = "房间ID", required = true) String roomId,
            @ApiParam(value = "最后一条消息id", required = true) int lastMsgId
    ) throws Exception {
        CommListResult<ChatMsg> data = chatMsgService.queryChatMsgList(roomId,lastMsgId,1, 20);
        return CommJSONResult.ok(data.getList(), data.getAttributes());
    }

}
