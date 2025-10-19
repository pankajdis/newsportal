package com.gofortrainings.newsportal.core.models;


import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gofortrainings.newsportal.core.services.CustomService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,resourceType = "newsportal/components/customSlingModelComponent")
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,extensions = ExporterConstants.SLING_MODEL_EXTENSION, selector = "geeks")
public class CustomSlingModel {

    @OSGiService
    private CustomService customService;
    @ValueMapValue
    @Default(values = "Title by model default")
    @JsonProperty("title1")
    private String title;

    @ValueMapValue
    @JsonProperty("description1")
    @Default(values = "Description default model")
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty("service message")
    public String getServiceMessage(){
        return customService != null ? customService.getCustomMessage() :"Service not available";
    }

    @ChildResource
    public List<Contact> contact;

    public List<Contact> getContact() {
        return contact;
    }
}
