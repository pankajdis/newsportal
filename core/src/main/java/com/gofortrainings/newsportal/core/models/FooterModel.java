package com.gofortrainings.newsportal.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModel {

    @ValueMapValue
    private String copyrightText;

    @ValueMapValue
    private String copyrightLink;

    @ValueMapValue
    private String description;

    @ChildResource(name = "socialIcon") // note: your dialog node name is 'socailIcon'
    private List<Resource> socialIcons;

    public String getCopyrightText() {
        return copyrightText;
    }

    public String getCopyrightLink() {
        return copyrightLink;
    }

    public String getDescription() {
        return description;
    }

    public List<Map<String, String>> getSocialIcons() {
        List<Map<String, String>> icons = new ArrayList<>();
        if (socialIcons != null) {
            for (Resource iconRes : socialIcons) {
                ValueMap vm = iconRes.getValueMap();
                Map<String, String> map = new HashMap<>();
                map.put("iconClass", vm.get("iconClass", ""));
                map.put("iconLink", vm.get("iconLink", ""));
                icons.add(map);
            }
        }
        return icons;
    }
}
