package top.soul.wsserver.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Zkclient {
    public static void main(String[] args) {
        System.out.println("正在启动Zookeepter");
        CuratorFramework client = ZookeepterFactory.create();
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/"+address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("服务器启动成功");
    }
}
