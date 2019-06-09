package top.soul.wsserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import top.soul.wsserver.ChatMsg;
import top.soul.wsserver.DataContent;
import top.soul.wsserver.UserChannelRef;
import top.soul.wsserver.enums.MsgActionEnum;
import top.soul.wsserver.utils.JsonUtils;

import java.util.*;

/**
 * @Description: 用户匹配功能03
 */
public class XmatchChatHandlerSoul extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channle
    public static ChannelGroup users =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();
        Channel currentChannel = ctx.channel();
        // 1. 获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();

        // 2. 判断消息类型，根据不同的类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {

            // 	2.1  当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRef.put(senderId, currentChannel);

        } else if (action == MsgActionEnum.CHAT.type) {

            //  2.2  聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            String receiverId = chatMsg.getReceiverId();
            String senderId = chatMsg.getSenderId();
            // 保存消息到数据库，并且标记为 未签收
            // 发送消息
            // 从全局用户Channel关系中获取接受方的channel

            Channel receiverChannel = UserChannelRef.get(receiverId);
            Channel receiverChannefail = UserChannelRef.get(senderId);


            if (receiverChannel == null) {
//                XmatchUserChannelRef.put(senderId, UserChannelRef.get(senderId));
                receiverChannefail.writeAndFlush(
                        new TextWebSocketFrame(
                                "对方用户已离线"));

            } else {
                // 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    // 用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    msgText));
                } else {
                   //  XmatchUserChannelRef.put(senderId, UserChannelRef.get(senderId));
                    receiverChannefail.writeAndFlush(
                            new TextWebSocketFrame(
                                    "对方用户已离线"));


                }
            }

        } else if(action == MsgActionEnum.SIGNED.type){
            String senderId = dataContent.getChatMsg().getSenderId();
            XmatchUserChannelRef.put(senderId, currentChannel);
            match();
        } else if(action == MsgActionEnum.OUT.type){



        }
    }

    static void match(){

        ;
        if(XmatchUserChannelRef.getManager().size()>=2){
            List<String> returnResult1 = new ArrayList<String>();
            Set<String> keySet = XmatchUserChannelRef.getManager().keySet();
            Iterator<String> it = keySet.iterator();
            while(it.hasNext()) {
                returnResult1.add(it.next());
            }

            while (returnResult1.size()>=2){
                String a = returnResult1.get(0);
                returnResult1.remove(0);
                String b = returnResult1.get(0);
                returnResult1.remove(0);
                // 通知a，b

                Channel aChannel = XmatchUserChannelRef.get(a);
                Channel bChannel = XmatchUserChannelRef.get(b);

                if (aChannel != null) {
                    aChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    b));
                }
                if (bChannel != null) {
                    bChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    a));
                }

                //移除
                XmatchUserChannelRef.del(a);
                XmatchUserChannelRef.del(b);
            }
        }
        if((XmatchUserChannelRef.getManager().size()>0)){

        }

    }
    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asShortText();
        users.remove(ctx.channel());
        HashMap<String, Channel> managerUser  = UserChannelRef.getManager();
        for(Map.Entry<String, Channel> user : managerUser.entrySet()){
            if(user.getValue().equals(ctx.channel())){
                UserChannelRef.getManager().remove(user.getKey());


            }
        }
        HashMap<String, Channel> xmatchmanager  =XmatchUserChannelRef.getManager();
        for(Map.Entry<String, Channel> xmatchuser : xmatchmanager.entrySet()){
            if(xmatchuser.getValue().equals(ctx.channel())){
                XmatchUserChannelRef.getManager().remove(xmatchuser.getKey());

            }
        }

        ctx.channel().close();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {


        users.remove(ctx.channel());
        HashMap<String, Channel> managerUser  = UserChannelRef.getManager();
        for(Map.Entry<String, Channel> user : managerUser.entrySet()){
            if(user.getValue().equals(ctx.channel())){
                UserChannelRef.getManager().remove(user.getKey());

            }
        }
        HashMap<String, Channel> xmatchmanager  =XmatchUserChannelRef.getManager();
        for(Map.Entry<String, Channel> xmatchuser : xmatchmanager.entrySet()){
            if(xmatchuser.getValue().equals(ctx.channel())){
                XmatchUserChannelRef.getManager().remove(xmatchuser.getKey());

            }
        }
        ctx.channel().close();
    }
}
