package com.chanshiyu.netty.session;

import com.chanshiyu.netty.attribute.Attributes;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author shiyu
 * @date 2019/9/13 14:48
 * @Description
 */
@Slf4j
public class SessionUtil {

    private static final Map<String, ConcurrentLinkedQueue<Channel>> mUserIdChannelMap = new ConcurrentSkipListMap<>();

    /**
     * 当前channel 保留的会话信息
     * @param channel channel
     * @return 会话
     */
    public static Session getSession(Channel channel) {
        if (channel == null) return null;
        return channel.attr(Attributes.SESSION).get();
    }

    public static Session getSession(ConcurrentLinkedQueue<Channel> channels) {
        Channel channel = getChannel(channels);
        if (channel == null) return null;
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(ConcurrentLinkedQueue<Channel> channels) {
        if (channels == null || channels.size() == 0) return null;
        return channels.iterator().next();
    }

    /**
     * 是否登录
     * @param channel channel
     * @return 是否登录
     */
    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }

    /**
     * 绑定会话信息
     * @param session 会员
     * @param channel 频道
     */
    public static void bindSession(Session session, Channel channel) {
        ConcurrentLinkedQueue<Channel> channels = mUserIdChannelMap.get(session.getUserId());
        if (channels == null) {
            ConcurrentLinkedQueue<Channel> objects = new ConcurrentLinkedQueue<>();
            objects.add(channel);
            mUserIdChannelMap.put(session.getUserId(), objects);
        } else {
            channels.add(channel);
        }
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解绑会话
     * @param channel 频道
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            String userId = session.getUserId();
            ConcurrentLinkedQueue<Channel> channels = mUserIdChannelMap.get(userId);
            if (channels != null) {
                channels.remove(channel);
            }
            if (channels != null && channels.size() == 0) {
                mUserIdChannelMap.remove(userId);
            }
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 获取对应用户ID
     */
    public static ConcurrentLinkedQueue<Channel> getUserIdChannel(String userId) {
        if (userId == null) return null;
        return mUserIdChannelMap.get(userId);
    }

}
