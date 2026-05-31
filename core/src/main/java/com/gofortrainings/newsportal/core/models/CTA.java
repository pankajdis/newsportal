package com.gofortrainings.newsportal.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CTA {
    @SlingObject
    private Resource resource;

    @ValueMapValue
    private String[] buttonStyle;

    private List<Map<String, String>> buttonList;

    @PostConstruct
    protected void init() {
        buttonList = new ArrayList<>();
    //    ValueMap ctaButtonsRes = resource.getValueMap();
    //    if (ctaButtonsRes != null) {
            for (String child : buttonStyle) {
                String style = child.toString();
                if (buttonStyle != null) {
                    Map<String, String> button = new HashMap<>();
                    button.put("class", style);
                    button.put("label", style.replace("btn-", ""));
                    buttonList.add(button);
                }
            }
    //    }
    }

    public List<Map<String, String>> getButtonList() {
        return buttonList;
    }
}
