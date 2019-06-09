package top.soul.wsserver.enums;

/**
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {

    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "开始匹配"),
    OUT(4, "退出匹配"),
    PULL_FRIEND(5, "拉取好友");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
