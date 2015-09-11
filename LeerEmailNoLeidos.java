import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class LeerEmailNoLeidos {
	public LeerEmailNoLeidos(){
		
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", Config.email, Config.password);
            
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            
            Message[] mensajes = inbox.search(unseenFlagTerm);
            
            for (int i = 0; i < mensajes.length; i++) {
            	
            	System.out.println("--------- INBOX ----------\n\n\n");
                
            	Message message = mensajes[i];
                String subject = message.getSubject();
                String Data = message.getSentDate().toString();
                
   
                String Contenido = Mensaje(message);
                int ID = message.getMessageNumber();
                
                
                Address[] in = message.getFrom();
                for (Address address : in) {
                    System.out.println("D:" + address.toString());
                }
                
                System.out.println("ID: " + ID);
                System.out.println("Fecha:" + Data);
                System.out.println("Título:" + subject);
                System.out.println("Contenido:" + Contenido);
                System.out.println("\n\n\n--------------\n\n\n");
                
                //Marca mensaje como leido
                inbox.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
            }
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
		
	}
	
	public static String Mensaje(Message message) throws MessagingException, IOException {
	  Object content = message.getContent();
	  if (content instanceof MimeMultipart) {
		  MimeMultipart multipart=(MimeMultipart)content;
		  if (multipart.getCount() > 0) {
			  BodyPart part=multipart.getBodyPart(0);
			  content=part.getContent();
		  }
	  }
	  if (content != null) {
		  return content.toString();
	  }
	  return null;
	}
}
