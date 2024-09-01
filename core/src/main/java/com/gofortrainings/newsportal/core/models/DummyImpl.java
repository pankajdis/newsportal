package com.gofortrainings.newsportal.core.models;

import org.apache.http.client.cache.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.gofortrainings.newsportal.core.services.DummyService;

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

}
