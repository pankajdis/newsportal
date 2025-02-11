package com.gofortrainings.newsportal.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.http.client.cache.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.gofortrainings.newsportal.core.services.DummyService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Dummy.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DummyImpl implements Dummy {

    @OSGiService
    DummyService dService;

    @Override
    public String getUserName() {
        String username = dService.getOsgiUserName();
        return username;
    }

    @Override
    public String getPassword() {
        String password = dService.getOsgiPassword();
        return password;
    }

    @Override
    public String getEnvironment() {
        String environment = dService.getOsgiEnvironment();
        return environment;
    }

    @Override
    public String getInstance() {
        String instance = dService.getOsgiInstance();
        return instance;
    }
    @ValueMapValue
    private String urlpath;

    public String getUrlpath() {
        return urlpath;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public SlingScriptHelper getScriptHelper() {
        return scriptHelper;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public SlingScriptHelper getSling() {
        return sling;
    }

    @SlingObject
    private Resource resource;

    @SlingObject
    private ResourceResolver resourceResolver; // Injects the ResourceResolver

    @SlingObject
    private SlingScriptHelper scriptHelper; // Injects the SlingScriptHelper

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private SlingScriptHelper sling;



}
