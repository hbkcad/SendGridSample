package email.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator
  extends Authenticator
{
  public PasswordAuthentication getPasswordAuthentication()
  {
    String username = "*****";
    String password = "*****";
    return new PasswordAuthentication(username, password);
  }
}
