package com.gofortrainings.newsportal.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface ArticleConfiguration {
    @AttributeDefinition(name = "Rest API URL",
    description = "REst API URL to fetch articles from third party")
    public  String articleRestUrl() default "https://gorest.co.in/public/v2/posts";

    @AttributeDefinition(name = "Enable/Disable Config")
    public boolean status() default true;
}
