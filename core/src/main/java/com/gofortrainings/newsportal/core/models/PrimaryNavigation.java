package com.gofortrainings.newsportal.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.*;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PrimaryNavigation {

    @SlingObject
    ResourceResolver resourceResolver;

    private String title;

    private String path;

    private List<NavigationItems> childList;

    public List<NavigationItems> getChildList() {
        childList = new ArrayList<>();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = pageManager.getPage(rootPath);
        Iterator<Page> pageIterator = page.listChildren();
        while (pageIterator.hasNext()){
            Page child = pageIterator.next();
            NavigationItems navigationItems = new NavigationItems(child.getTitle(), child.getPath());
            /*Page page1 = pageManager.getPage(child.getPath());
            while (page1.listChildren().hasNext()){
                Page child1 = child.listChildren().next();
                String path = child1.getPath();
            }*/
        //    child.listChildren().next().getTitle();
            childList.add(navigationItems);
       //     childList.add(child.listChildren().next().getTitle());
        }
        return childList;
    }

    public String getTitle() {

   /* //    PageManager pageManager = resource.adaptTo(PageManager.class);
    //    Resource pageResource = resourceResolver.getResource(hit.getPath());
        Resource pageResource = resourceResolver.getResource(rootPath);
        Page page = pageResource.adaptTo(Page.class);
    //    ValueMap valueMap=page.adaptTo(ValueMap.class);

        String title = page.getTitle();
        return title;*/
// page.getContentResource().getValueMap().get("jcr:title");

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = pageManager.getPage(rootPath);
        String title = null;
        if (page != null) {
            title = page.getTitle() != null ? page.getTitle() : page.getName();
        }
        return title;

        /*Resource resourceObj = resource.getResource(rootPath);

        if (resource != null) {
            // You can now access properties, adapt it, etc.
            ValueMap properties = resource.adaptTo(ValueMap.class);
            String title = properties.get("jcr:title", String.class);
            return title;
        }
        return null;*/
    }

    public String getPath() {
        return rootPath + ".html";
    }

    @ValueMapValue
    private String rootPath;

    public String getRootPath() {
        return rootPath;
    }
}
