import api.HbaseConnection;
import api.HbaseUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseConTest {
    public static void main(String[] args) {
//        Connection connection =  HbaseConnection.getHbaseConnection();
//        System.out.println(connection);
//        try {
//            Table table =HbaseConnection.getTable("ObjectStore2");
//            System.out.println(table);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HbaseUtil.createTable("721yun",new String[]{"fileInfo","filedata"});
//        System.out.println("001");
//        HbaseUtil.putRow("721yun","rowkey00000001","fileInfo","name","25515115111.jpg");
//        System.out.println("002");
//        HbaseUtil.putRow("721yun","rowkey00000001","fileInfo","time","20150101");
//        HbaseUtil.putRow("721yun","rowkey00000001","fileInfo","user","wangpengpeng");
//        HbaseUtil.putRow("721yun","rowkey00000001","filedata","64codebase","dasdhfuyevbchasvdyavscvasvdgasasdasc");
        Result result=HbaseUtil.getRow("721yun","rowkey00000001");
        System.out.println(result.getValue(Bytes.toBytes("fileInfo"),Bytes.toBytes("name")));
    }

}
