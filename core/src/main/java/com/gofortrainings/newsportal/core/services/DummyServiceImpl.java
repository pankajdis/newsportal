package com.gofortrainings.newsportal.core.services;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;
@Component(service = DummyService.class, immediate = true)
@Designate(ocd = DummyServiceImpl.Siteconfig.class)
public class DummyServiceImpl implements DummyService {

    @ObjectClassDefinition(name = "Site Information", description = "Site Information description")
    public @interface Siteconfig {


        @AttributeDefinition(name = "Site User Name", description = "Site User Name Description", type = AttributeType.STRING)
        public String siteUserName() default "admin";

        @AttributeDefinition(name = "Site User Passowrd", description = "Site User Password Description", type = AttributeType.STRING)
        public String sitePassword();

        @AttributeDefinition(name = "Site Environment", description = "Site Environment Description",
                options = {
                        @Option(label = "DevEnvironment", value = "dev environment"),
                        @Option(label = "QAEnvironment", value = "qa environment"),
                        @Option(label = "ProdEnvironment", value = "prod environment")
                },
                type = AttributeType.STRING)
        public String siteEnvironment();

        @AttributeDefinition(name = "Site Instance", description = "Site Environment Description", type = AttributeType.STRING)
        public String siteInstance();

    }

    String siteusername;
    String sitepassword;
    String siteenvironment;
    String siteinstance;

    @Activate
    public void activate(Siteconfig activeservicve) {
        this.siteusername = activeservicve.siteUserName();
        this.sitepassword = activeservicve.sitePassword();
        this.siteenvironment = activeservicve.siteEnvironment();
        this.siteinstance = activeservicve.siteInstance();
    }

    @Override
    public String getOsgiUserName() {
        return siteusername;
    }

    @Override
    public String getOsgiPassword() {
        return sitepassword;
    }

    @Override
    public String getOsgiEnvironment() {
        return siteenvironment;
    }

    @Override
    public String getOsgiInstance() {
        return siteinstance;
    }

}
