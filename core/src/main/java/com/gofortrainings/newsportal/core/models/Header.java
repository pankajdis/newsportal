package com.gofortrainings.newsportal.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class Header {

    private static final Logger LOG = LoggerFactory.getLogger(Header.class);

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Resource resource;

    @ValueMapValue
    private String logoLink;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String altText;

    @ChildResource(name = "navigations")
    private List<PrimaryNavigation> navigationsResource;

    public String getLogoLink() {
        return logoLink;
    }

    public String getFileReference() {
        return fileReference;
    }

    public String getAltText() {
        return altText;
    }

    public List<PrimaryNavigation> getNavigationsResource() {
        return navigationsResource;
    }

}
