package com.chanshiyu.netty.disruptor.consumer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.chanshiyu.netty.protocol.command.Command;
import com.chanshiyu.netty.protocol.request.CreateRoomRequestPacket;
import com.chanshiyu.netty.protocol.request.LoginRequestPacket;
import com.chanshiyu.netty.protocol.request.SendMessageRequestPacket;
import com.chanshiyu.netty.protocol.response.CreateRoomResponsePacket;
import com.chanshiyu.netty.protocol.response.LoginResponsePacket;
import com.chanshiyu.netty.protocol.response.AcceptMessageResponsePacket;
import com.chanshiyu.netty.protocol.response.SendMessageResponsePacket;
import com.chanshiyu.netty.session.Session;
import com.chanshiyu.netty.session.SessionUtil;
import com.chanshiyu.pojo.ChatMsg;
import com.chanshiyu.pojo.Room;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.service.ChatMsgService;
import com.chanshiyu.service.RoomService;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shiyu
 * @date 2019/9/12 15:18
 * @Description
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageConsumerImpl extends MessageConsumer {

    // 时间
    static SimpleDateFormat ChatDateFormat;

    private RoomService roomService;

    private UserService userService;

    private ChatMsgService chatMsgService;

    {
        roomService = SpringUtil.getBean(RoomService.class);
        userService = SpringUtil.getBean(UserService.class);
        chatMsgService = SpringUtil.getBean(ChatMsgService.class);
    }

    static {
        ChatDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        ChatDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) {
        super.onEvent(event);
        // 上下文
        ChannelHandlerContext ctx = event.getCtx();
        Channel channel = ctx.channel();
        // 命令字
        Integer command = event.getPacket().getCommand();
        log.info("onEvent-->{}", command);
        switch (command) {
            case Command.LOGIN_REQUEST:
                // 登陆处理
                login(ctx, (LoginRequestPacket) event.getPacket());
                break;
            case Command.CREATE_ROOM_REQUEST:
                createRoom(ctx, (CreateRoomRequestPacket) event.getPacket());
                break;
            case Command.SEND_MESSAGE_REQUEST:
                // 消息处理
                msg(ctx, (SendMessageRequestPacket) event.getPacket());
                break;
            default:
                log.error("command -> {}, 该消息未被处理", command);
                break;
        }
    }

    /**
     * login处理
     */
    private void login(ChannelHandlerContext ctx, LoginRequestPacket packet) {
        Session session = new Session(packet.getUserId(), packet.getUsername(), packet.getNickname());
        SessionUtil.bindSession(session, ctx.channel());
        LoginResponsePacket response = new LoginResponsePacket(packet.getUserId(), packet.getUsername(), packet.getNickname(),true, "登录成功");
        ctx.writeAndFlush(response);
    }

    /**
     * 创建房间
     */
    private void createRoom(ChannelHandlerContext ctx, CreateRoomRequestPacket packet)  {
        String users = packet.getUsers();
        // 获取或创建房间
        Room room = roomService.queryRoomByUsers(users);
        if (room != null) {
            // 返回已存在房间
            CreateRoomResponsePacket response = new CreateRoomResponsePacket(room.getId(), room.getName(), room.getUsers(), room.getCreateUser(), room.getCreateTime(), room.getIcon());
            ctx.writeAndFlush(response);
        } else {
            try {
                // 创建新房间
                Room newRoom = new Room();
                newRoom.setName(packet.getName());
                newRoom.setUsers(packet.getUsers());
                newRoom.setCreateUser(packet.getCreateUserId());
                // 取房间第一个用户头像为图标
                String[] usersStr = packet.getUsers().split(",");
                String userId = usersStr[0];
                Users users1 = userService.queryUserById(userId);
                if (users1 != null) {
                    newRoom.setIcon(users1.getAvatar());
                }
                Room result = roomService.createRoom(newRoom);
                CreateRoomResponsePacket response = new CreateRoomResponsePacket(result.getId(), result.getName(), result.getUsers(), result.getCreateUser(), result.getCreateTime(), result.getIcon());
                ctx.writeAndFlush(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 消息处理
     */
    private void msg(ChannelHandlerContext ctx, SendMessageRequestPacket packet) {
        try {
            Channel channel = ctx.channel();
            String roomId = packet.getRoomId();
            Session session = SessionUtil.getSession(ctx.channel());
            Room room = roomService.queryRoomById(roomId);

            if (room != null) {
                Users sendUser = userService.queryUserById(session.getUserId());
                // 消息入库
                int msgId = chatMsgService.createChatMsg(new ChatMsg(null, room.getId(), sendUser.getId(), sendUser.getNickname(), sendUser.getAvatar(), packet.getMsg(), new Date()));
                // 找出房间所有用户并广播
                String roomUsers = room.getUsers();
                String[] roomUsersId = roomUsers.split(",");

                for (String s : roomUsersId) {
                    ConcurrentLinkedQueue<Channel> ch = SessionUtil.getUserIdChannel(s);
                    // 自己不推消息
                    if (!s.equals(sendUser.getId())) {
                        sendMsg(ch, msgId, room.getId(), sendUser.getId(), sendUser.getNickname(), sendUser.getAvatar(), packet.getMsg(), new Date());
                    }
                }
                // 消息发送成功
                sendMsgSuccess(channel, packet.getMsgIndex(), msgId, new Date().getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存并转发消息
     */
    private void sendMsg(ConcurrentLinkedQueue<Channel> channels, int msgId, String roomId, String sendUserId, String sendNickname, String sendAvatar, String msg, Date date) {
        if (channels != null && channels.size() > 0) {
            log.info("广播消息-->{}", channels.size());
            AcceptMessageResponsePacket response = new AcceptMessageResponsePacket(msgId, roomId, sendUserId, sendNickname, sendAvatar, msg, date.getTime());
            channels.forEach(channel -> channel.writeAndFlush(response));
        }
    }

    /**
     * 消息发送成功
     */
    private static void sendMsgSuccess(Channel channel, int msgIndex, int msgId, long date) {
        channel.writeAndFlush(new SendMessageResponsePacket(msgId, msgIndex, date));
    }

}
