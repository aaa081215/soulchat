package top.soul.wsserver;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: step 01 Netty启动类
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            WsServer.getInstance().start();
            XmatchWsServer.getInstance().start();
        }
    }
}
