package com.zxtc.collectiontools.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

//import com.google.common.base.Strings;

/**
 * 邮件工具
 * @author
 *
 */
public class EmailTool {
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    //是否授权，一般都是true
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";

    private Properties properties;
    private ContentType contentType;
    private MimeMessage message;

    public static enum ContentType {
        HTML(0),PIC(1), ATTACH(2);
        private byte value;
        ContentType(int value) {
            this.value = (byte) value;
        }
        public byte value() {
            return value;
        }
        public static ContentType fromByte(byte value) {
            for (ContentType dt : values()) {
                if (dt.value == value) {
                    return dt;
                }
            }
            return null;
        }
    }
    private EmailTool(ContentType type){
        properties = new Properties();
        this.contentType=type;
    }
    public EmailTool host(String host){
        properties.put(MAIL_SMTP_HOST, host);
        return this;
    }
    public EmailTool auth(String auth){
        properties.put(MAIL_SMTP_AUTH, auth);
        return this;
    }
    public EmailTool protocol(String protocol){
        properties.put(MAIL_TRANSPORT_PROTOCOL, protocol);
        return this;
    }
    public EmailTool port(Integer port){
        properties.put(MAIL_SMTP_PORT, port);
        return this;
    }
    public EmailTool sockPort(Integer sockPort){
        properties.put(MAIL_SMTP_SOCKETFACTORY_PORT, sockPort);
        properties.put(MAIL_SMTP_PORT,sockPort);
        return this;
    }
    public EmailTool sockClass(String sockClass){
        properties.put(MAIL_SMTP_SOCKETFACTORY_CLASS, sockClass);
        properties.put("mail.smtp.socketFactory.fallback", "false");
        return this;
    }
    public EmailTool starttls(String starttls){
        properties.put(MAIL_SMTP_STARTTLS,starttls);
        return this;
    }

    private  Session createSession(Properties properties, final String emailBox, final String pwd) {
        return Session.getInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailBox, pwd);
            }
        });
    }

    public EmailTool login(String emailBox, String pwd, String userName) throws UnsupportedEncodingException, MessagingException{
        this.message = new MimeMessage(createSession(properties, emailBox,pwd));
        /*if(!userName.isEmpty() || userName != null){
            message.setFrom(new InternetAddress(emailBox,userName));
        }*/
        //发送方name
        message.setFrom(new InternetAddress(emailBox,userName));
        return this;
    }

    /**
     * 添加收件人
     * @param receiver 收件人
     * @param cc 抄送人
     * @param bcc  暗送人
     * @return
     * @throws AddressException
     * @throws MessagingException
     */
    private boolean dealAddress(String receiver[], String cc[], String bcc[]) throws AddressException, MessagingException{
        // 设置邮件的收件人 cc表示抄送 bcc 表示暗送
        for (String rec:receiver) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(rec));
        }
        //抄送人
        if(cc!=null){
            for(String c:cc){
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(c));
            }
        }
        //暗送
        if(bcc!=null){
            for(String bc:bcc){
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bc));
            }
        }
        return true;
    }
    /**
     * 发送邮件带附件
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private boolean sendAttach(String content, String[]filePath) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=utf8");
        MimeMultipart mm2 = new MimeMultipart();
        mm2.addBodyPart(text);
        if (filePath != null) {
            for (String path:filePath) {
                if (!path.isEmpty() || path != null) {
                    MimeBodyPart attch = new MimeBodyPart();
                    DataHandler dh1 = new DataHandler(new FileDataSource(path));
                    attch.setDataHandler(dh1);
                    String filename1 = dh1.getName();
                    // MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
                    attch.setFileName(MimeUtility.encodeText(filename1));
                    mm2.addBodyPart(attch);
                }
            }
        }
        mm2.setSubType("mixed");
        message.setContent(mm2);
        message.saveChanges(); // 保存修改
        return true;
    }
    /**
     * 发送邮件带图片
     * @return
     * @throws MessagingException
     */
    private boolean sendPic(String content, String filePath[]) throws MessagingException{
        MimeMultipart mm = new MimeMultipart();//设置正文与图片之间的关系
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=utf8");
        mm.addBodyPart(text);
        if (filePath != null) {
            for (String path:filePath) {
                MimeBodyPart img = new MimeBodyPart();
                DataHandler dh = new DataHandler(new FileDataSource(path));
                img.setDataHandler(dh);
                // 创建图片的一个表示用于显示在邮件中显示
                System.out.println(path.substring(path.lastIndexOf("/")+1));
                img.setContentID(path.substring(path.lastIndexOf("/")+1));
                mm.addBodyPart(img);
            }
        }
        mm.setSubType("related");// 设置正文与图片之间的关系
        message.setContent(mm);
        message.saveChanges(); // 保存修改

        return true;
    }
    /**
     * 发送html
     * @return
     * @throws MessagingException
     */
    private boolean sendHtml(String content) throws MessagingException{
        // 创建邮件的正文
        message.setContent(content, "text/html;charset=utf8");
        return true;
    }
    public boolean send(String receiver[], String cc[], String bcc[], String subject, String content, String filePath[])
            throws AddressException, MessagingException, UnsupportedEncodingException {
        //处理收件人
        dealAddress(receiver,cc,bcc);
        // 设置邮件的主题
        message.setSubject(subject);
        switch(this.contentType){
            case ATTACH:
                sendAttach(content,filePath);
                break;
            case PIC:
                sendPic(content,filePath);
                break;
            case HTML:
                sendHtml(content);
        }

        //解决MIME的问题，与web application里WEB-INF/web.xml里没有指定application holder一样的道理，可直接在MultiPartMail里指定
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;;x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;;x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;;x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;;x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;;x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

        Transport.send(message);// 发送邮件
        return true;
    }

    public static EmailTool getInstance(ContentType type){
        return new EmailTool(type);
    }

    /*public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
        String subject="测试发送ok";
        //发送图片时要注竟
        String content="好好8<img src='cid:bb.jpg'>9999";
        String filePath[]=new String[]{"C:/bb.jpg"};
//    	EmailTool email=EmailTool.getInstance(EmailTool.ContentType.PIC).host("mail.xxxx.com").auth("true").protocol("smtp").port(25).login("xx@xx.com", "pwd", "张三");
        EmailTool email=EmailTool.getInstance(EmailTool.ContentType.PIC).host("smtp.qq.com").auth("true").sockPort(465)
                .sockClass("javax.net.ssl.SSLSocketFactory").protocol("smtp").login("xxx@qq.com", "xxx", "李四");
        email.send(new String[]{"xx@xx.com"},null,null,  subject, content, filePath);
    }*/
}

