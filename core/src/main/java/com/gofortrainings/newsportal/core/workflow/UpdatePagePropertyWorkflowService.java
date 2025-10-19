package com.gofortrainings.newsportal.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.List;

@Component(service = WorkflowProcess.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Update Page Property Workflow Process",
                "process.label=Sankham update page property Workflow Process" // Specify the label here
        })
public class UpdatePagePropertyWorkflowService implements WorkflowProcess{
        @Reference
        private Replicator replicator;
        @Override
        public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
                ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
                Session session = workflowSession.adaptTo(Session.class);
                String payloadPath = workItem.getWorkflowData().getPayload().toString();
                List<HistoryItem> historyItemList = workflowSession.getHistory(workItem.getWorkflow());
                Resource payloadPage = resourceResolver.getResource(payloadPath + "/jcr:content");
                if (payloadPage == null) {
                        throw new WorkflowException("Unable to get the resource for path: " + payloadPath);
                }
                try {
                        replicator.replicate(session, ReplicationActionType.ACTIVATE,payloadPath);
                        ReplicationStatus status = replicator.getReplicationStatus(session, payloadPath);
                        if (status != null && status.isActivated()) {
                                ModifiableValueMap properties = payloadPage.adaptTo(ModifiableValueMap.class);
                                if (properties != null) {
                                        String lastPublishedBy = historyItemList.size() > 1 && historyItemList.get(1) != null
                                                ? historyItemList.get(1).getUserId()
                                                : "";
                                        properties.put("lastPublishedBypropertybybackend", lastPublishedBy);
                                        try {
                                                resourceResolver.commit();
                                        } catch (PersistenceException e) {
                                                throw new RuntimeException("Failed to commit changes", e);
                                        }
                                }
                        }
                } catch (ReplicationException e) {
                        throw new RuntimeException(e);
                }
        }
}
