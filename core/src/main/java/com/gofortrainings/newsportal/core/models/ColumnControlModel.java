package com.gofortrainings.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ColumnControlModel {
    @ValueMapValue
    private String layout; // selected layout value from ACS Commons list

    public List<String> getColumns() {
        List<String> cols = new ArrayList<>();

        if (layout == null) {
            return cols;
        }

        switch (layout) {
            case "50% 50%":
                cols = Arrays.asList("col-md-6", "col-md-6");
                break;
            case "25% 25% 25% 25%":
                cols = Arrays.asList("col-md-3", "col-md-3", "col-md-3", "col-md-3");
                break;
            case "33% 33% 33%":
                cols = Arrays.asList("col-md-4", "col-md-4", "col-md-4");
                break;
            default:
                cols = Arrays.asList("col-md-12");
        }
        return cols;
    }
}
