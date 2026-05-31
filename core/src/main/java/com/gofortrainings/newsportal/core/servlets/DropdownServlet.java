package com.gofortrainings.newsportal.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(service = Servlet.class,immediate = true)
@SlingServletResourceTypes(methods = HttpConstants.METHOD_GET,
                        resourceTypes = "newsportal/components/datasource"
                            )
public class DropdownServlet extends SlingSafeMethodsServlet {
    private static final String BASE_PATH = "/content/newsportal/us/en/home-page";

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Map<String, String> pageMap = getPageTitlePathMap(resolver);

        // Create synthetic resources (datasource items)
        List<Resource> resourceList = new ArrayList<>();
        pageMap.forEach((title, path) -> {
            ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
            valueMap.put("text", title);
            valueMap.put("value", path);
            resourceList.add(new ValueMapResource(
                    resolver, new ResourceMetadata(), "nt:unstructured", valueMap));
        });

        // Create the datasource object and attach to request
        DataSource ds = new SimpleDataSource(resourceList.iterator());
        request.setAttribute(DataSource.class.getName(), ds);
    }

    /**
     * Builds a HashMap of pageTitle → pagePath.
     */
    private Map<String, String> getPageTitlePathMap(ResourceResolver resolver) {
        Map<String, String> pageMap = new LinkedHashMap<>(); // keep order
        Resource baseResource = resolver.getResource(BASE_PATH);

        if (baseResource != null) {
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            Page rootPage = pageManager.getContainingPage(baseResource);

            if (rootPage != null) {
                Iterator<Page> children = rootPage.listChildren();
                while (children.hasNext()) {
                    Page child = children.next();
                    pageMap.put(child.getTitle(), child.getPath());
                }
            }
        }
        return pageMap;
    }

}
