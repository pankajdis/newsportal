package com.gofortrainings.newsportal.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(service = NPUtilService.class)
public class NPUtilService {

    @Reference
    ResourceResolverFactory factory;
    public ResourceResolver getResourceResolver() throws LoginException {
        ResourceResolver resolver = null;
        Map<String,Object> props = new HashMap<>();
        props.put(ResourceResolverFactory.SUBSERVICE,"npsubservice");
        resolver = factory.getServiceResourceResolver(props);
        return resolver;
    }
}
