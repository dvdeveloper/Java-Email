import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnviarEmail {
	
	public EnviarEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.user", Config.email);
        props.put("mail.smtp.host", Config.host);
        props.put("mail.smtp.port", Config.port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", Config.port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);     
            MimeMessage msg = new MimeMessage(session);
            msg.setText(Config.titulo);
            msg.setSubject(Config.Mensaje);
            msg.setFrom(new InternetAddress(Config.email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(Config.enviar_email));
            Transport.send(msg);
        } catch (Exception mex) {
        	System.out.println("Hubo un error");
            mex.printStackTrace();
        }
    }
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(Config.email, Config.password);
        }
    }
}
