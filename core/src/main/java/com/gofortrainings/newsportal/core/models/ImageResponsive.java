package com.gofortrainings.newsportal.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageResponsive {

    @SlingObject
    SlingHttpServletRequest servletRequest;

    public List<Images> getImages() {
        return images;
    }

    @Inject
    @Via("resource")
    public List<Images> images;

}
