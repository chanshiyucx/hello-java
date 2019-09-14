package com.chanshiyu.controller;

import com.chanshiyu.pojo.Room;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.vo.UsersVO;
import com.chanshiyu.service.RoomService;
import com.chanshiyu.util.CommJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/14 17:34
 * @Description
 */
@RestController
@RequestMapping("/room")
@Api(value = "房间管理", tags = {"房间管理Controller"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(value = "用户详情", notes = "用户详情")
    @GetMapping("/detail")
    public CommJSONResult<Room> detail(@ApiParam(value = "房间ID", required = true) String roomId) throws Exception {
        Room room = roomService.queryRoomById(roomId);
        return CommJSONResult.ok(room);
    }

}
