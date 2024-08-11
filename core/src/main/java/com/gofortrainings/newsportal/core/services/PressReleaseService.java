package com.gofortrainings.newsportal.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class PressReleaseService {
    private static final Logger LOG = LoggerFactory.getLogger(PressReleaseService.class);

    @Reference
    ArticleService articleService;

    @Activate
    public void activate(){
        String articles = articleService.getArticles();
        LOG.info("Press Release Service - Inside Activate Method");
        LOG.info("Response -{}",articles);
    }

    @Deactivate
    public void deactivate(){
        LOG.info("Press Release Service - Inside Deactivate Method");
    }

    @Modified
    public void modified(){
        LOG.info("Press Release Service - Inside Modified Method");
    }
}
