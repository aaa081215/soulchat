package top.soul.wsserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Description: 聊天 01
 */

@Component
public class WsServer {
    // 定义一对线程组
    // 主线程组, 用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
    private EventLoopGroup mainGroup;
    // 从线程组, 老板线程组会把任务丢给他，让手下线程组去做任务
    private EventLoopGroup subGroup;
    // netty服务器的创建, ServerBootstrap 是一个启动类
    private ServerBootstrap server;
    private ChannelFuture future;
    public WsServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }

    public static WsServer getInstance() {
        return Single.instance;
    }

    public void start() {
        this.future = server.bind(8089);

        System.err.println("chat websocket server started in ws://localhost:8089/ws.");
    }

    private static class Single {
        static final WsServer instance = new WsServer();
    }

}
