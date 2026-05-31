package com.gofortrainings.newsportal.core.services.impl;


import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.gofortrainings.newsportal.core.services.MailService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ContactEmailService.class,immediate = true)
public class ContactEmailService {
    @Reference
    private MessageGatewayService messageGatewayService;

    public void sendEmail(String firstName, String lastName, String email, String subject, String messageBody) {
        try {
            Email emailMsg = new SimpleEmail();
            emailMsg.setMsg(buildEmailBody(firstName, lastName, email, subject, messageBody));
            emailMsg.setSubject("Contact Us Form Submission");
            emailMsg.setCharset(EmailConstants.UTF_8);
            emailMsg.addTo("abc159@gmail.com");
            /*for (String recipient : recipients) {
                emailMsg.addTo(recipient);
            }*/

            MessageGateway<Email> messageGateway = messageGatewayService.getGateway(SimpleEmail.class);

            if (messageGateway != null) {
                messageGateway.send(emailMsg);
            } else {
                throw new IllegalStateException("No message gateway found for Email");
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    private String buildEmailBody(String firstName, String lastName, String email, String subject, String messageBody) {
        return "New Contact Form Submission:\n\n"
                + "First Name: " + firstName + "\n"
                + "Last Name: " + lastName + "\n"
                + "Email: " + email + "\n"
                + "Subject: " + subject + "\n"
                + "Message:\n" + messageBody;
    }

}
