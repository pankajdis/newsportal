package com.gofortrainings.newsportal.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NavigationItems {

    private String title;
    private String path;


    public NavigationItems(String title, String path) {
        this.title = title;
        this.path = path;
    }


    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path+".html";
    }

}
