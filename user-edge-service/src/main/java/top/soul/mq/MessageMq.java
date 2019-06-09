package top.soul.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageMq {




    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public boolean sendMq(String email,String code){
        Map map = new HashMap();
        map.put("email", email);
        map.put("code", code);
//        jmsMessagingTemplate.convertAndSend("emailmq",map);
        jmsMessagingTemplate.convertAndSend("emailmq2",map);
        return true;
    }

}
