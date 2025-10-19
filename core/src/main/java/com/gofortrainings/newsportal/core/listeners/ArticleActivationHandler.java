package com.gofortrainings.newsportal.core.listeners;

import com.day.cq.replication.ReplicatedAction;
import com.day.cq.replication.ReplicationAction;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventHandler.class,
            property = {
                    EventConstants.EVENT_TOPIC+"="+ReplicationAction.EVENT_TOPIC,
                    EventConstants.EVENT_FILTER+"= (& (type=ACTIVATE)(paths=/content/newsportal/us/en/home-page/*))"
            },
        immediate = true
)
public class ArticleActivationHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleActivationHandler.class);
    @Override
    public void handleEvent(Event event) {
            LOG.error("Inside handle event method ......");
    }
}
