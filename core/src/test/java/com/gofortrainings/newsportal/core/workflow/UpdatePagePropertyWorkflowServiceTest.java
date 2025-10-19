package com.gofortrainings.newsportal.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePagePropertyWorkflowServiceTest {

    @InjectMocks
    private UpdatePagePropertyWorkflowService workflowService;

    @Mock
    private Replicator replicator;

    @Mock
    private WorkflowSession workflowSession;

    @Mock
    private WorkItem workItem;

    @Mock
    private WorkflowData workflowData;

    @Mock
    private Workflow workflow;

    @Mock
    private ResourceResolver resourceResolver;

    @Mock
    private Session session;

    @Mock
    private Resource payloadResource;

    @Mock
    private ModifiableValueMap modifiableValueMap;

    @Mock
    private MetaDataMap metaDataMap;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessFlow() throws Exception {
        String payloadPath = "/content/news/sample";

        // Mock workflow setup
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn(payloadPath);
        when(workItem.getWorkflow()).thenReturn(workflow);

        when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(workflowSession.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.getResource(payloadPath + "/jcr:content")).thenReturn(payloadResource);

        // Mock replication status
        ReplicationStatus replicationStatus = mock(ReplicationStatus.class);
        when(replicationStatus.isActivated()).thenReturn(true);
        when(replicator.getReplicationStatus(session, payloadPath)).thenReturn(replicationStatus);

        // Mock history items
        HistoryItem historyItem1 = mock(HistoryItem.class);
        HistoryItem historyItem2 = mock(HistoryItem.class);
        when(historyItem2.getUserId()).thenReturn("admin");
        List<HistoryItem> history = Arrays.asList(historyItem1, historyItem2);
        when(workflowSession.getHistory(workflow)).thenReturn(history);

        // Mock value map
        when(payloadResource.adaptTo(ModifiableValueMap.class)).thenReturn(modifiableValueMap);

        // Execute
        workflowService.execute(workItem, workflowSession, metaDataMap);

        // Verify replication and property update
        verify(replicator).replicate(session, ReplicationActionType.ACTIVATE, payloadPath);
        verify(modifiableValueMap).put("lastPublishedBypropertybybackend", "admin");
        verify(resourceResolver).commit();
    }

    @Test
    void testExecute_ResourceNotFound_ThrowsException() {
        String payloadPath = "/content/missing";
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn(payloadPath);
        when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(resourceResolver.getResource(payloadPath + "/jcr:content")).thenReturn(null);

        assertThrows(WorkflowException.class, () ->
                workflowService.execute(workItem, workflowSession, metaDataMap));
    }

    @Test
    void testExecute_ReplicationException_ThrowsRuntimeException() throws Exception {
        String payloadPath = "/content/error";
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn(payloadPath);
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(workflowSession.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.getResource(payloadPath + "/jcr:content")).thenReturn(payloadResource);

        // ✅ Throw exception from replicate() instead (it declares ReplicationException)
        doThrow(new ReplicationException("Failed"))
                .when(replicator)
                .replicate(session, ReplicationActionType.ACTIVATE, payloadPath);

        assertThrows(RuntimeException.class, () ->
                workflowService.execute(workItem, workflowSession, metaDataMap));
    }

    @Test
    void testExecute_PersistenceException_ThrowsRuntimeException() throws Exception {
        String payloadPath = "/content/sample";
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn(payloadPath);
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(workflowSession.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.getResource(payloadPath + "/jcr:content")).thenReturn(payloadResource);

        // Mock replication status
        ReplicationStatus replicationStatus = mock(ReplicationStatus.class);
        when(replicationStatus.isActivated()).thenReturn(true);
        when(replicator.getReplicationStatus(session, payloadPath)).thenReturn(replicationStatus);

        // Mock history items
        HistoryItem historyItem1 = mock(HistoryItem.class);
        List<HistoryItem> history = Collections.singletonList(historyItem1);
        when(workflowSession.getHistory(workflow)).thenReturn(history);

        when(payloadResource.adaptTo(ModifiableValueMap.class)).thenReturn(modifiableValueMap);
        doThrow(new PersistenceException("Commit failed")).when(resourceResolver).commit();

        assertThrows(RuntimeException.class, () ->
                workflowService.execute(workItem, workflowSession, metaDataMap));
    }
}
