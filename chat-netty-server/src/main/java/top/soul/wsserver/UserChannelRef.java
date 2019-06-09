package top.soul.wsserver;


import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @Description: 用户id与channel对应
 */
public class UserChannelRef {
    private static HashMap<String, Channel> manager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }

    public static HashMap<String, Channel> getManager() {
        return manager;
    }

    public static void setManager(HashMap<String, Channel> manager) {
        UserChannelRef.manager = manager;
    }
}
