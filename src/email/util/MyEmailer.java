package email.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MyEmailer
{
  static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
  static final String SMTP_AUTH_USER = "**************************";
  static final String SMTP_AUTH_PWD = "*******";
  
  public static void main(String[] args)
    throws Exception
  {
    System.out.println("Executing WebJob");
    new MyEmailer().SendMail();
  }
  
  public void SendMail()
    throws Exception
  {
    System.out.println("sending mail");
    Properties properties = new Properties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.host", "smtp.sendgrid.net");
    properties.put("mail.smtp.port", Integer.valueOf(25));
    properties.put("mail.smtp.auth", "true");
    System.out.println("properties set");
    
    Authenticator auth = new SMTPAuthenticator();
    Session mailSession = Session.getDefaultInstance(properties, auth);
    
    MimeMessage message = new MimeMessage(mailSession);
    Multipart multipart = new MimeMultipart("alternative");
    BodyPart part1 = new MimeBodyPart();
    part1.setText("Hello, WebJob. Thank you, WebJob");
    BodyPart part2 = new MimeBodyPart();
    part2.setContent(
      "<p>Hello,</p><p>WebJob <b>mail</b>.</p><p>Thank you,<br>WebJob</br></p>", "text/html");
    multipart.addBodyPart(part1);
    multipart.addBodyPart(part2);
    message.setFrom(new InternetAddress("from@mail.com"));
    message.addRecipient(Message.RecipientType.TO, 
      new InternetAddress("to@mail.com"));
    message.setSubject("WebJob Mail");
    message.setContent(multipart);
    
    Transport transport = mailSession.getTransport();
    
    transport.connect();
    System.out.println("transport object  " + transport.toString());
    
    transport.sendMessage(message, message.getAllRecipients());
    System.out.println("recipients   " + message.getAllRecipients().toString());
    
    transport.close();
  }
}
