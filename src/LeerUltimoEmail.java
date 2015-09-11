import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

public class LeerUltimoEmail {
	public LeerUltimoEmail(){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", Config.email, Config.password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            
            System.out.println("------- INBOX -------\n\n");
            for (Address address : in) {
                System.out.println("D:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("Fecha:" + msg.getSentDate());
            System.out.println("Título:" + msg.getSubject());
            System.out.println("Contenido:" + bp.getContent());
            System.out.println("\n\n\n--------------\n\n\n");
            
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
	}
}
