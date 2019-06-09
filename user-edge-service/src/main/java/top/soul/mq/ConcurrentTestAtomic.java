package top.soul.mq;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentTestAtomic {
    public static int clirntTotal=5000;
    public static int threadTootal=100;
    public  static  int count=0;
    public static void main(String[] args) throws InterruptedException {
        Long start=new Date().getTime();
        System.out.println("start-time:"+start);
        ExecutorService executorService= Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch=new CountDownLatch(clirntTotal);
        for(int i=0;i<clirntTotal;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    add();
                    countDownLatch.countDown();
                }
            });
        }
        //判断所有线程执行完
        countDownLatch.await();
        executorService.shutdown();
        Long end=new Date().getTime();
        System.out.println("ends-time:"+end);
        System.out.println("耗时："+(end-start)+"毫秒");
    }
    public  static void add(){
        count++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}