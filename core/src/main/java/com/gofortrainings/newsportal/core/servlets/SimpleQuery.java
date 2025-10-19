package com.gofortrainings.newsportal.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class,immediate = true)
@SlingServletPaths("/bin/simple/query")
public class SimpleQuery extends SlingSafeMethodsServlet {

    @Reference
    QueryBuilder queryBuilder;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Map<String,String> predicate = new HashMap();
        predicate.put("type","cq:Page");
        predicate.put("path","/content/newsportal/us/en");
        predicate.put("p.limit","-1");

        ResourceResolver resourceResolver =request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);
        SearchResult result = query.getResult();
        List<Hit> hits = result.getHits();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Hit hit :hits){
            try {
                Resource resource = hit.getResource();
                Page page = resource.adaptTo(Page.class);
                JsonObjectBuilder job = Json.createObjectBuilder();
                job.add("title",page.getTitle());
                job.add("path", page.getPath());
                jsonArrayBuilder.add(job);

            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }

        }
        response.getWriter().write(jsonArrayBuilder.build().toString());
    }
}
