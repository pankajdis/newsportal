package com.gofortrainings.newsportal.core.schedulers;


import com.gofortrainings.newsportal.core.services.SimpleSchedulerConfiguration;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service= PracticeScheduledTask.class, immediate = true)
@Designate(ocd= SimpleSchedulerConfiguration.class)
public class PracticeScheduledTask implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(PracticeScheduledTask.class);
    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(final SimpleSchedulerConfiguration config) {

        logger.error(" Inside @activate method called");

        // Execute this method to add scheduler.
        addScheduler(config);

    }

    // Add all configurations to Schedule a scheduler depending on name and expression.
    public void addScheduler(SimpleSchedulerConfiguration config) {
        logger.error("Scheduler addScheduler method called");
        if (config.enable_scheduler()) {
            ScheduleOptions options = scheduler.EXPR(config.scheduler_expression());
            options.name(config.scheduler_name());
            options.canRunConcurrently(config.concurrent_scheduler());

            // Add scheduler to call depending on option passed.
            scheduler.schedule(this, options);
            logger.error("Scheduler added successfully name='{}'", config.scheduler_name());
        } else {
            logger.error("SimpleScheduledTask disabled");
            scheduler.unschedule(config.scheduler_name());
        }
    }


    // Custom method to deactivate or unschedule scheduler
    public void removeScheduler(SimpleSchedulerConfiguration config) {
        logger.error("Scheduler removeScheduler method called");
        scheduler.unschedule(config.scheduler_name());
    }

    // On deactivate component it will unschedule scheduler
    @Deactivate
    protected void deactivate(SimpleSchedulerConfiguration config) {
        logger.error("Inside @Deactivate method called");
        removeScheduler(config);
    }

    // On component modification change status will remove and add scheduler
    @Modified
    protected void modified(SimpleSchedulerConfiguration config) {
        logger.error("Inside @Modified method called");
        removeScheduler(config);
        addScheduler(config);
    }

    // run() method will get call every minute
    @Override
    public void run() {
        logger.error("PracticeScheduledTask run >>>>>>>>>>>");
    }
}
