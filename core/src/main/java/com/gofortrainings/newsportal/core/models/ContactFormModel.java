package com.gofortrainings.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactFormModel {

    @ValueMapValue private String companyTitle;
    @ValueMapValue private String companyAddress;
    @ValueMapValue private String companyPhone;
    @ValueMapValue private String companyEmail;

    @ValueMapValue private String firstNameLabel;
    @ValueMapValue private String lastNameLabel;
    @ValueMapValue private String emailLabel;
    @ValueMapValue private String subjectLabel;
    @ValueMapValue private String messageLabel;

    @ValueMapValue private boolean additionalFormFields;

    public String getCompanyTitle() { return companyTitle; }
    public String getCompanyAddress() { return companyAddress; }
    public String getCompanyPhone() { return companyPhone; }
    public String getCompanyEmail() { return companyEmail; }

    public String getFirstNameLabel() { return firstNameLabel != null ? firstNameLabel : "Your First Name"; }
    public String getLastNameLabel() { return lastNameLabel != null ? lastNameLabel : "Your Last Name"; }
    public String getEmailLabel() { return emailLabel != null ? emailLabel : "Your Email"; }
    public String getSubjectLabel() { return subjectLabel != null ? subjectLabel : "Subject"; }
    public String getMessageLabel() { return messageLabel != null ? messageLabel : "Please write something for us"; }

    public boolean isAdditionalFormFields() { return additionalFormFields; }
}

