import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class LeerTodosEmail {
	
	public LeerTodosEmail(){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", Config.email, Config.password);
            
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] mensajes = inbox.getMessages();
            
            for (int i = 0; i < mensajes.length; i++) {
            	
            	System.out.println("--------- INBOX ----------\n\n\n");
                
            	Message message = mensajes[i];
                String subject = message.getSubject();
                String Data = message.getSentDate().toString();
                String Contenido = message.getContent().toString();
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
                
            }
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        
	}
}
