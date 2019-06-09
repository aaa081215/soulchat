package top.soul.wsserver.Task;

import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WsTask implements Runnable {

    ChannelGroup clients = null;

    public WsTask(ChannelGroup clients) {
        System.out.println("Task Start");
        this.clients = clients;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(1000);
                clients.writeAndFlush(
                        new TextWebSocketFrame(
                                "回传测试"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
