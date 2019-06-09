package top.soul.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Component
public class MqConsummerTast {

//    // 发件人 账号和密码
//    public static final String MY_EMAIL_ACCOUNT = "17864308253@163.com";
//    public static final String MY_EMAIL_PASSWORD = "aA111111";
//
//    // SMTP服务器(这里用的163 SMTP服务器)
//    public static final String MEAIL_163_SMTP_HOST = "smtp.163.com";
//    public static final String SMTP_163_PORT = "25";// 端口号,这个是163使用到的;QQ的应该是465或者875

    // 发件人 账号和密码
    public static final String MY_EMAIL_ACCOUNT = "1203269511@qq.com";
    public static final String MY_EMAIL_PASSWORD = "wlnzmgvnasfxhdee";

    // SMTP服务器(这里用的163 SMTP服务器)
    public static final String MEAIL_163_SMTP_HOST = "smtp.qq.com";
    public static final String SMTP_163_PORT = "25";// 端口号,这个是163使用到的;QQ的应该是465或者875

    @JmsListener(destination = "emailmq")
    public void readMsg(Map map) {
        System.out.println("mq接收消息："+ map.get("email")+"->"+ map.get("code"));
        doSend((String) map.get("email"), (String) map.get("code"));
    }
    private static void doSend(String RECEIVE_EMAIL_ACCOUNT,String msg){
        Properties p = new Properties();
        p.setProperty("mail.smtp.host", MEAIL_163_SMTP_HOST);
        p.setProperty("mail.smtp.port", SMTP_163_PORT);
        p.setProperty("mail.smtp.socketFactory.port", SMTP_163_PORT);
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");

        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);
            }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            // 发件人
            message.setFrom(new InternetAddress(MY_EMAIL_ACCOUNT));
            // 收件人和抄送人
            message.setRecipients(Message.RecipientType.TO, RECEIVE_EMAIL_ACCOUNT);
		    message.setRecipients(Message.RecipientType.CC, RECEIVE_EMAIL_ACCOUNT);
            // 内容(这个内容还不能乱写,有可能会被SMTP拒绝掉;多试几次吧)
            message.setSubject("Soul 验证码");
            message.setContent(msg, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
            message.setSentDate(new Date());//设置发信时间
            message.saveChanges();//存储邮件信息

            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
