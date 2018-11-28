package com.qa.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

import com.qa.base.TestBase;

//import com.qa.util.ReadEmails;

public class ReadEmails extends TestBase{
	
	Properties properties = null;
	private Session session = null;
	private Store store = null;
	private Folder inbox = null;
	private String userName = prop.getProperty("mail_username");	// provide user name
	private String password = prop.getProperty("mail_password");	// provide password
	private String mail_port = prop.getProperty("mail_portNumber");	// provide port number
	private String mail_hostID = prop.getProperty("mail_host");	// provide port number
	private String mail_transport_protocolID = prop.getProperty("mail_transport_protocol");
	String strMsgContent;
	String strReturnValue;
	Boolean strMailFound = false;
	

	public String readMails(String strFirstName, String strFrom, String strTo) throws IOException, InterruptedException {

		properties = new Properties();
		int timer =150;	// check for mail in Inbox for given seconds
	//set up property	
		properties.setProperty("mail.host", mail_hostID);	//"imap.gmail.com" Internet Message Access Protocol(IMAP) for reading mails
		properties.setProperty("mail.port", mail_port);	// g-mail port number "995"
		properties.setProperty("mail.transport.protocol",mail_transport_protocolID );	//g-Mail transport Protocol "imaps"
	// session instance	
		session = Session.getInstance(
				properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
						}
					}	
				);	
		try {
			store = session.getStore("imaps");
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			System.out.println("wait a moment..");
			System.out.println("searching for mail...");
			do {	
				Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false)); 
				//System.out.println("Number of mails = " + messages.length);
				if (messages.length > 0 ) {
					for (int i = 0; i<messages.length ; i++) { 
						Message message = messages[i]; 
						Address[] messageTO = message.getRecipients(RecipientType.TO);	//Receiver email address
						Address[] from = message.getFrom();		// sender email address
						String strAddressTo = messageTO[0].toString().trim();
						String strAddressFrom = from[0].toString().trim();	//
						if (strAddressFrom.equals(strFrom)) {		// search for address 'FROM'		
							if (strAddressTo.equals(strTo)) {		// search for address 'TO'	
								strMsgContent = message.getContent().toString();
								if (strMsgContent.contains(strFirstName)) {
									strMailFound = true;
									strReturnValue = strMsgContent.toString();
									break;
								} // if
							} // if
							else {
								if (messages.length-i == 1) {
									strMailFound = false;
									strReturnValue = "No mail send to '"+ strTo +"'";
								} //if
							//retry mail-check	
								timer = timer - 1;
								if (timer<0) {
									break;
								}
								else {
									Thread.sleep(1000);
								}
							} //else	
						} // if
						else {
							if (messages.length-i == 1) {
								strMailFound = false;
								strReturnValue = "No mail received from '"+ strFrom +"'";
							} //if
						//retry mail-check	
							timer = timer - 1;
							if (timer<0) {
								break;
							}
							else {
								Thread.sleep(1000);
							}
						} // else	
					} //for
				} //if
				else {
					strMailFound = false;
					strReturnValue = "Inbox is empty";
					timer = timer - 1;
					if (timer<0) {
						break;
					}
					else {
						Thread.sleep(1000);
					}
				} // else
				if (strMailFound.equals(true)) {
					break;
				}
			} while (timer>0);
			inbox.close(true); 
			store.close(); 
			} //Try 
		catch (NoSuchProviderException e) { 
			e.printStackTrace(); 
			} 
		catch (MessagingException e) { 
			e.printStackTrace(); 
			}
		return strMailFound.toString()+", "+strReturnValue;
			
	} // Method readMails 

	
/*
//------------------------	
// below methods have not been used as of now---
		public void processMessageBody(Message message) { 
			try { 
			Object content = message.getContent(); 
			// check for string // then check for multiple part 
			if (content instanceof String) { 
				System.out.println(content); 
				} //if 
				else if (content instanceof Multipart) { 
					Multipart multiPart = (Multipart) content; 
					procesMultiPart(multiPart); 
					} //else if
				else if (content instanceof InputStream) { 
					InputStream inStream = (InputStream) content; 
					int ch; 
					while ((ch = inStream.read()) != -1) { 
						System.out.write(ch); 
						} // while
					} // else if
			} //try 
			catch (IOException e) { 
				e.printStackTrace(); 
				} 
			catch (MessagingException e) { 
				e.printStackTrace(); 
				}
		} //method  processMessageBody

//------------------------
	public void procesMultiPart(Multipart content) {
		 try { 
			 int multiPartCount = content.getCount(); 
			 for (int i = 0; i < multiPartCount; i++) { 
				 BodyPart bodyPart = content.getBodyPart(i); 
				 Object o; o = bodyPart.getContent(); 
		
				if (o instanceof String) { 
					System.out.println(o); 
					} //if
				else if (o instanceof Multipart) { 
					procesMultiPart((Multipart) o); 
					} //else if
			 } // for
		 } //method procesMultiPart 
		 
		catch (IOException e) { 
			e.printStackTrace(); 
			} 
		
		catch (MessagingException e) { 
			e.printStackTrace(); 
			}  
		} //method  procesMultiPart	
		
*/
		
//for stand-alone run------------------------
//	public static void main(String[] args) { 
//		ReadEmails sample = new ReadEmails(); 
//		sample.readMails(); 
//		} 

	
}
