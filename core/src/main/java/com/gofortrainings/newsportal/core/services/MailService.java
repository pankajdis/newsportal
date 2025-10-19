package com.gofortrainings.newsportal.core.services;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = MailService.class,immediate = true)
public class MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Reference
    MessageGatewayService messageGatewayService;


    public void sendEmail(String toAddress, String subject, String messageBody) throws EmailException {
        MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
        HtmlEmail email = new HtmlEmail();
        email.addTo(toAddress);
        email.setSubject(subject);
        email.setMsg(messageBody);
        email.setFrom("singhpk159@gmail.com");

        if (messageGateway != null) {
            messageGateway.send((HtmlEmail) email);
            LOG.info("✅ Email sent successfully to {}", toAddress);
        } else {
            LOG.error("❌ Message Gateway not found.");
        }
    }
}
