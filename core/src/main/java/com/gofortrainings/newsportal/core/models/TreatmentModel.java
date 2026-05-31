package com.gofortrainings.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TreatmentModel {

    @ValueMapValue
    private String treatment;

    @ValueMapValue
    private String backgroundImage;

    public String getTreatment() {
        return treatment;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

}
