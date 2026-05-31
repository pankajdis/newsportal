package com.gofortrainings.newsportal.core.models;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel {
    @SlingObject
    private ResourceResolver resolver;

    @SlingObject
    private Resource resource;

    public List<Page> getTrail() {
        List<Page> pages = new ArrayList<>();

        PageManager pm = resolver.adaptTo(PageManager.class);
        Page currentPage = pm.getContainingPage(resource);

        while (currentPage != null) {
            pages.add(0, currentPage); // add to front
            currentPage = currentPage.getParent();
        }

        return pages;
    }
}
