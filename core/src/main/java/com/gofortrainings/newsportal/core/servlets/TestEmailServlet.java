package com.gofortrainings.newsportal.core.servlets;

import com.gofortrainings.newsportal.core.services.MailService;
import org.apache.commons.mail.EmailException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class,property = {
        "sling.servlet.paths=/bin/sendemail"
})

public class TestEmailServlet extends SlingAllMethodsServlet {
    @Reference
    private MailService emailService;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        try {
            emailService.sendEmail("pks230394@gmail.com", "Test Email from AEM", "Hello from AEM local environment!");
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        response.getWriter().write("Email sent (check FakeSMTP window)!");
    }
}
