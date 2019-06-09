package top.soul.thrift;


import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.soul.thrift.user.UserService;

/**
 * Created by Michael on 2017/10/30.
 */
@Component
public class ServiceProvider {

    @Value("localhost")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int serverPort;

    public UserService.Client getUserService() {

        return getService(serverIp, serverPort, ServiceType.USER);
    }


    public <T> T getService(String ip, int port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip, port, 10000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);

        TServiceClient result = null;
        switch (serviceType) {
            case USER:
                result = new UserService.Client(protocol);
                break;
        }
        return (T) result;
    }

    private enum ServiceType {
        USER,
        MESSAGE
    }

}
