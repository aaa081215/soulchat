package api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HbaseConnection {
    private static final HbaseConnection INSTANCE = new HbaseConnection();
    private static Configuration configuration;
    private static Connection connection;
    private HbaseConnection(){
        try {
            if(configuration==null){
                configuration = HBaseConfiguration.create();
                configuration.set("hbase.zookeeper.quorum","172.17.0.1:2181");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private Connection getConnection(){
        if(connection==null || connection.isClosed()){
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static Connection getHbaseConnection(){
        return INSTANCE.getConnection();
    }
    public static Table getTable(String tableName) throws IOException {
        return INSTANCE.getConnection().getTable(TableName.valueOf(tableName));
    }
    public static void close() {
      if(connection!=null){
          try {
              connection.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    }
}
