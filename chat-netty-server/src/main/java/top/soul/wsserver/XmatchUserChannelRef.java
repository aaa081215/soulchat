package top.soul.wsserver;


import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @Description: 用户id与channel对应
 */
public class XmatchUserChannelRef {
    private static HashMap<String, Channel> Xmanager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        Xmanager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return Xmanager.get(senderId);
    }

    public static void del(String senderId) {
        Xmanager.remove(senderId);
    }

    public static HashMap<String, Channel> getManager() {
        return Xmanager;
    }

    public static void setManager(HashMap<String, Channel> manager) {
        XmatchUserChannelRef.Xmanager = manager;
    }
}
