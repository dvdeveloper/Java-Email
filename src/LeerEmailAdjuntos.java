import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class LeerEmailAdjuntos {
	public LeerEmailAdjuntos(){
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
                
                int ID = message.getMessageNumber();
                
                Address[] in = message.getFrom();
                for (Address address : in) {
                    System.out.println("D:" + address.toString());
                }
                
                System.out.println("ID: " + ID);
                System.out.println("Fecha:" + Data);
                System.out.println("Título:" + subject);
                System.out.println("\n\n\n--------------\n\n\n");
                
                //Marca mensaje como leido
                inbox.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
                
                DescargarAdjuntos(message);
            }
            
        } catch (Exception mex) {
            mex.printStackTrace();
        }
	}
	
	public static void DescargarAdjuntos(Message message) throws MessagingException, IOException {
      System.out.println( "<"+message.getFrom()[0] + "> " + message.getSubject());
      Multipart multipart = (Multipart)message.getContent();
      
        for (int j = 0; j < multipart.getCount(); j++) {
            BodyPart bodyPart = multipart.getBodyPart(j);
            if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                if (bodyPart.getContent().getClass().equals(MimeMultipart.class)) {
                    MimeMultipart mimemultipart = (MimeMultipart)bodyPart.getContent();
                    System.out.println("Número de archivos adjuntos "+mimemultipart.getCount());
                    for (int k=0;k<mimemultipart.getCount();k++) {
                        if (mimemultipart.getBodyPart(k).getFileName() != null) {
                        	System.out.println("     > Guardando archivo : "+bodyPart.getFileName());
                        	GuardarArchivo(mimemultipart.getBodyPart(k).getFileName(), mimemultipart.getBodyPart(k).getInputStream());
                        }
                    }
                }
              continue;
            }
            System.out.println("     > Guardando archivo : "+bodyPart.getFileName());
            GuardarArchivo(bodyPart.getFileName(), bodyPart.getInputStream());
        }
    }
	
	
	public static void GuardarArchivo(String FileName, InputStream is) throws IOException {
        File f = new File(Config.ruta_archivo + FileName);
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[4096];
        int bytesRead;
        while((bytesRead = is.read(buf))!=-1) {
            fos.write(buf, 0, bytesRead);
        }
        fos.close();
    }
}
