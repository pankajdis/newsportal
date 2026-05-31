package com.gofortrainings.newsportal.core.servlets;
import com.gofortrainings.newsportal.core.services.impl.ContactEmailService;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/sendcontactemail",
                "sling.servlet.methods=POST"
        })
public class ContactFormServlet extends SlingAllMethodsServlet {

    @Reference
    private ContactEmailService contactEmailService;
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        contactEmailService.sendEmail(firstName,lastName,email,subject,message);
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource userResource = resourceResolver.getResource("/content/mail");
        Resource usersResource = resourceResolver.getResource(userResource + firstName);
        if(userResource !=null && usersResource ==null ){
            Map<String,Object> prop = new HashMap<>();
            prop.put("firstName",firstName);
            prop.put("lastName",lastName);
            prop.put("emailId",email);
            resourceResolver.create(userResource,firstName,prop);
            resourceResolver.commit();
        }
        response.getWriter().write("User created successfully");
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", "success");
        jsonResponse.addProperty("message", "Your message has been sent. Thank you!");

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
