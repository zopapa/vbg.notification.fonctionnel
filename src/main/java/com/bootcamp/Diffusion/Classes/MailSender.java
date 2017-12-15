/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.Diffusion.Classes;

import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author edwigegédéon
 */
public class MailSender {

    private static String USER_NAME = "bignonfebron";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "amuztadjitadjidation"; // GMail password
    private static String[] RECIPIENT = {"mboabello@yahoo.fr", "archange.jb@gmail.com"};
    private static String SUBJECT = "CPS CHITé enfant d'autrui est entrain de se faire battre !!";

    public static void sendFromGMail(String body) throws MessagingException {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = RECIPIENT;
        String subject = SUBJECT;

        System.setProperty("https.protocols", "TLSv1.1");
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

//  public  void sender() throws MessagingException{
//     List<String> mailMsgs = getMsgToSend("sendMail"); 
//      for (String mailMsg : mailMsgs) {
//         sendFromGMail(mailMsg); 
//      }
//  }
}
