package com.gofortrainings.newsportal.core.models;
import com.adobe.cq.wcm.core.components.models.Image;
import lombok.experimental.Delegate;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},adapters = {ImageTestModel.class,Image.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageTestModelImpl implements ImageTestModel, Image {

    @ValueMapValue
    private String imagePrefix;


    public String getImagePrefix() {
        return imagePrefix;
    }

    @Override
    public String getAlt() {
        String alt = image.getAlt();
        return StringUtils.isNotBlank(imagePrefix)
                ? alt + " " + imagePrefix
                : alt;
    }
    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate(excludes = CustomImageExclusions.class)
    private Image image;

    private interface CustomImageExclusions {
        String getAlt();
    }
}
