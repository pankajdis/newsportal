package com.gofortrainings.newsportal.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(service = Servlet.class, immediate = true)
@SlingServletPaths("/bin/simple/query/component")
public class QueryComponent extends SlingSafeMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(QueryComponent.class);
    @Reference
    QueryBuilder queryBuilder;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Session session = request.getResourceResolver().adaptTo(Session.class);
        Map<String, String> predicate = new HashMap();
        predicate.put("path", "/content/newsportal/us/en");
        predicate.put("type", "nt:unstructured");
        predicate.put("property", "sling:resourceType");
        predicate.put("property.value", "newsportal/components/helloworld");
        predicate.put("p.limit", "-1");
        Query query = queryBuilder.createQuery(PredicateGroup.create(predicate),session);
        SearchResult result = query.getResult();
        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        List<Hit> list = result.getHits();
        // Use Set to avoid duplicate pages
        Set<String> containingPages = new HashSet<>();
        for (Hit hit : list) {
            Page containingPage;
            try {
                Resource componentResource = hit.getResource();
                containingPage = pageManager.getContainingPage(componentResource);
                if (containingPage != null) {
                    LOG.info("componentResource found in page:{}", containingPage.getPath());
                    //    containingPages.add(containingPage.getPath());
                }
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
            containingPages.add(containingPage.getPath());
                    // Return response

        }
        response.getWriter().write(containingPages.toString());
    }
}
