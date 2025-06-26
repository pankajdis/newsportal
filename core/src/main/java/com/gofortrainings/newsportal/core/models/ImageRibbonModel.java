package com.gofortrainings.newsportal.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageRibbonModel {

    @Inject
    @Named("images")
    private List<Resource> imageResources;

    List<String> imagePaths = new ArrayList<>();

    public List<String> getImagePaths() {
        if (imageResources != null && imageResources.size() == 7) {
            for (Resource res : imageResources) {
                String path = res.getValueMap().get("image", String.class);
                if (StringUtils.isNotBlank(path)) {
                    imagePaths.add(path);
                }
            }
        }
        return imagePaths;
    }

    public boolean isValidImageCount() {
        return imagePaths.size() == 7;
    }
}
