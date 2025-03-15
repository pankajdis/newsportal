package com.gofortrainings.newsportal.core.services.impl;

import com.gofortrainings.newsportal.core.services.JcrNodeService;
import com.gofortrainings.newsportal.core.services.NPUtilService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;


@Component(service = JcrNodeService.class, immediate = true)
public class JcrNodeServiceImpl implements JcrNodeService {
    @Reference
    NPUtilService npUtilService;

    @Override
    public String updateNode(String path, String propertyName, String propertyValue) throws RepositoryException {
        Node node = null;
        try {
            ResourceResolver resourceResolver = npUtilService.getResourceResolver();
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                node = resource.adaptTo(Node.class);
                if (node != null) {
                    node.setProperty(propertyName, propertyValue);
                    resourceResolver.commit(); // Commit the change to the JCR
                }
            }
        } catch (PersistenceException | RepositoryException e) {

        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        return ("Node updated: " + node.getPath());

    }
}
