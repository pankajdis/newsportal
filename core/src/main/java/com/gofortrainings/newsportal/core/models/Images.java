package com.gofortrainings.newsportal.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Images {

    public String getSrc() {
        return src;
    }

    public String getText() {
        return text;
    }

    public String getDesc() {
        return desc;
    }

    @Inject
    private String src;

    @Inject
    private String text;

    @Inject
    private String desc;

}
