package com.chanshiyu.netty.codec;

import com.alibaba.fastjson.JSONObject;
import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import com.chanshiyu.netty.protocol.request.CreateRoomRequestPacket;
import com.chanshiyu.netty.protocol.request.HeartBeatRequestPacket;
import com.chanshiyu.netty.protocol.request.LoginRequestPacket;
import com.chanshiyu.netty.protocol.request.SendMessageRequestPacket;
import com.chanshiyu.netty.protocol.response.CreateRoomResponsePacket;
import com.chanshiyu.netty.protocol.response.HeartBeatResponsePacket;
import com.chanshiyu.netty.protocol.response.LoginResponsePacket;
import com.chanshiyu.netty.protocol.response.AcceptMessageResponsePacket;
import com.chanshiyu.netty.serialize.Serializer;
import com.chanshiyu.netty.serialize.impl.JSONSerializer;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shiyu
 * @date 2019/9/12 10:44
 * @Description: 编解码
 */
@Slf4j
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Integer, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        // 心跳
        packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
        // 登陆
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        // 消息
        packetTypeMap.put(Command.SEND_MESSAGE_REQUEST, SendMessageRequestPacket.class);
        packetTypeMap.put(Command.SEND_MESSAGE_RESPONSE, AcceptMessageResponsePacket.class);
        // 房间
        packetTypeMap.put(Command.CREATE_ROOM_REQUEST, CreateRoomRequestPacket.class);
        packetTypeMap.put(Command.CREATE_ROOM_RESPONSE, CreateRoomResponsePacket.class);
        // 加入
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 编码
     * @param packet 需要编码的对象
     */
    public TextWebSocketFrame encode(Packet packet) {
        String text = Serializer.DEFAULT.serializeJSON(packet);
        return new TextWebSocketFrame(text);
    }

    /**
     * 解码
     * @param msg 消息
     * @return 解码后的对象
     */
    public Packet decode(TextWebSocketFrame msg) {
        // 命令
        String text = msg.text();
        JSONObject parse = (JSONObject) JSONObject.parse(text);
        Byte algorithm = parse.getByte("algorithm");
        Integer command = parse.getInteger("command");
        Serializer serializer = getSerializer(algorithm);
        // 解析json
        String data = parse.getString("data");
        Class<? extends Packet> requestType = getRequestType(command);
        if (requestType != null) {
            return serializer.deserialize(requestType, data);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(int command) {
        return packetTypeMap.get(command);
    }

}
