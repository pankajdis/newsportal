package com.gofortrainings.newsportal.core.servlets;


import com.adobe.xfa.ut.Key;
import com.gofortrainings.newsportal.core.services.JcrNodeService;
import com.gofortrainings.newsportal.core.services.NPUtilService;
import com.gofortrainings.newsportal.core.services.impl.JcrNodeServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component(service = Servlet.class, immediate = true)
@SlingServletPaths("/bin/test")
public class JcrNodeServiceServlet extends SlingAllMethodsServlet {

    @Reference
    JcrNodeService jcrNodeService;
    @Reference
    NPUtilService npUtilService;
    private  static final Logger LOG = LoggerFactory.getLogger(JcrNodeServiceServlet.class);
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resourceResolver = null;//request.getResourceResolver();
        try {
            resourceResolver = npUtilService.getResourceResolver();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        Resource userResource = resourceResolver.getResource("/content/newsportal/us/en/home-page/jcr:content/root/container/container/image_list");
        JsonObjectBuilder userJson =  Json.createObjectBuilder();
        if (userResource != null){
            ValueMap prop = userResource.getValueMap();
            userJson.add("prntPage",prop.get("parentPage",String.class));
            userJson.add("tagsMatch",prop.get("tagsMatch",String.class));
        }
        response.setContentType("text/plain");
        response.getWriter().write(userJson.build().toString());
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailId = request.getParameter("emailId");
        String mobile = request.getParameter("mobile");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource userResource = resourceResolver.getResource("/content/user");
        Resource usersResource = resourceResolver.getResource(userResource + userid);
        if(userResource !=null && usersResource ==null ){
            Map<String,Object> prop = new HashMap<>();
            prop.put("firstName",firstName);
            prop.put("lastName",lastName);
            prop.put("emailId",emailId);
            prop.put("mobile",mobile);
            resourceResolver.create(userResource,userid,prop);
            resourceResolver.commit();
        }
        response.getWriter().write("User created successfully");
    }

    @Override
    protected void doPut( SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        /*LOG.trace("===============================Printing LOGS from info put============================");
        LOG.debug("===============================Printing LOGS from info put============================");
        LOG.info("===============================Printing LOGS from info put============================");
        LOG.warn("===============================Printing LOGS from info put============================");
        LOG.error("===============================Printing LOGS from info put============================");

        final Logger LOG = LoggerFactory.getLogger(JcrNodeServiceServlet.class);
        String path = request.getParameter("path");
        String propName = request.getParameter("firstName");
        String propValue = request.getParameter("firstValue");
        Node node = null;
        try {
            ResourceResolver resourceResolver = npUtilService.getResourceResolver();
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                node = resource.adaptTo(Node.class);
                if (node != null) {
                    node.setProperty(propName, propValue);
                    LOG.info("firstName,updated value {} & {}",propName,propValue);
                    LOG.info("firstName --> {}",propName);
                    resourceResolver.commit(); // Commit the change to the JCR
                }
            }
        } catch (PersistenceException | RepositoryException e) {

        } catch (LoginException e) {
            throw new RuntimeException(e);
        }*/
        String path = request.getParameter("path");
        String propName = request.getParameter("firstName");
        String propValue = request.getParameter("firstValue");
        String tocheck = null;
        try {
            tocheck = jcrNodeService.updateNode(path,propName,propValue);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().write(tocheck + "property updated");
    }
}
