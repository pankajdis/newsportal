package com.gofortrainings.newsportal.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import static org.osgi.service.metatype.annotations.AttributeType.*;

@ObjectClassDefinition(name="Simple Scheduler Configuration name",description = "Simple Scheduler Configuration Description")
public @interface SimpleSchedulerConfiguration {
    @AttributeDefinition(name="Scheduler name",description = "Scheduler name description")
    public String scheduler_name() default "practice";

    @AttributeDefinition(name="Cron job expression",description = "Scheduler Cron job expression")
    public String scheduler_expression() default "0 * * * * ?";

    @AttributeDefinition(name="Enable Scheduler",description = "Enable Scheduler Description")
    public boolean enable_scheduler() default true;

    @AttributeDefinition(
            name = "Concurrent Scheduler",
            description = "Concurrent Scheduler",
            type = AttributeType.BOOLEAN)
    public boolean concurrent_scheduler() default false;

    @AttributeDefinition(
            name = "Custom Property",
            description = "Custom Property",
            type = AttributeType.STRING)
    public String customProperty() default "Test";
}
