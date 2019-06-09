package top.soul.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageMqTest {

    @Autowired
    private MessageMq messageMq;

    @Test
    public void sendMq() {
        int clintTotal=5000;
        Long start=new Date().getTime();
        System.out.println("start-time:"+start);
        ExecutorService executorService= Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch=new CountDownLatch(clintTotal);
        for(int i=0;i<clintTotal;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        messageMq.sendMq("1203269511@qq.com","l");;
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        Long end=new Date().getTime();
        System.out.println("ends-time:"+end);
        System.out.println("处理"+clintTotal+"个请求**耗时："+(end-start)+"毫秒");
    }
}