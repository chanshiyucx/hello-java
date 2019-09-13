package com.chanshiyu.controller;

import com.chanshiyu.pojo.Room;
import com.chanshiyu.service.RoomService;
import com.chanshiyu.util.CommJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author shiyu
 * @date 2019/9/13 10:30
 * @Description: 房间相关 controller
 */
@Slf4j
@RestController
@RequestMapping("/room")
@Api(value = "房间管理", tags = {"房间管理Controller"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(value = "创建房间", notes = "创建房间")
    @PostMapping("/create")
    public CommJSONResult<Room> create(@ApiParam(value = "创建房间", required = true) @Valid @RequestBody Room bean) throws Exception {
        String[] usersStr = bean.getUsers().split(",");
        TreeSet<String> treeSet = new TreeSet<String>();
        Collections.addAll(treeSet, usersStr);
        String usersStrSort = "";
        for(String s : treeSet){
            if (!StringUtils.isEmpty(usersStrSort)) {
                usersStrSort += ",";
            }
            usersStrSort += s;
        }
        // 判断房间是否存在
        Room isExistRoom = roomService.queryRoomByUsers(usersStrSort);
        if (isExistRoom != null) {
            // 返回已有房间
            return CommJSONResult.ok(isExistRoom);
        } else {
            // 创建新的房间
            bean.setUsers(usersStrSort);
            Room result = roomService.createRoom(bean);
            return CommJSONResult.ok(result);
        }
    }

}
