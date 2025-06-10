package utilies;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailReporting {
    public static void sendEmail() {
        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.put("mail.host", host);
        props.put("mail.defaultEncoding", "UTF-8");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.trust", "*");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("kishorautomations@gmail.com", "AutoKishor");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohta.kishor97@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mohta.kishor97@gmail.com"));
            message.setSubject("Testing Subject");
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This is message body");
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = Constant.SCREENSHOTS;
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart2);
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("=====Email Sent=====");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}