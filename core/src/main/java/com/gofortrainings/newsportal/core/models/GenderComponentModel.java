package com.gofortrainings.newsportal.core.models;

import javax.annotation.PostConstruct;
        import javax.inject.Inject;
        import javax.inject.Named;

        import org.apache.sling.api.SlingHttpServletRequest;
        import org.apache.sling.models.annotations.Default;
        import org.apache.sling.models.annotations.DefaultInjectionStrategy;
        import org.apache.sling.models.annotations.Model;
        import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenderComponentModel {

    private static final Logger LOG= LoggerFactory.getLogger(GenderComponentModel.class);


    @Inject
    @Named("gender")
    @Default(values = "Male") // Default value if not set in the dialog
    private String selectedGender;

    @ValueMapValue
    private String name;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }

    @ValueMapValue
    private String address;

    @ValueMapValue
    private String message;

    public String getSelectedGender() {
        return selectedGender;
    }

    @PostConstruct
    protected void init(){
        LOG.info("=============Printing LOGS=================================");

    }
}
