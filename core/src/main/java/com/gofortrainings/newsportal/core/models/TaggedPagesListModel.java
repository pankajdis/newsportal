package com.gofortrainings.newsportal.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,resourceType = "newsportal/component/tagSelector")
@Exporter(
        name = "jackson",
        extensions ="json",
        selector = "geeks"
)
public class TaggedPagesListModel {
    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    @ValueMapValue
    private String[] selectedTags;  // tags selected in dialog

    private List<PageInfo> taggedPages = new ArrayList<>();

    public List<PageInfo> getTaggedPages() {
        return taggedPages;
    }

    @PostConstruct
    protected void init() throws RepositoryException {
        if (selectedTags == null || selectedTags.length == 0) {
            return;
        }
        Map<String, String> predicates = new HashMap<>();
        predicates.put("path", "/content/newsportal/us/en"); // base search path
        predicates.put("type", "cq:Page");
        predicates.put("property", "jcr:content/cq:tags");
        for (int i = 0; i < selectedTags.length; i++) {
            predicates.put("property."+ (i + 1)+"" +
                    "_value", selectedTags[i]);
        }
        // Use AND condition to get pages having all selected tags
        predicates.put("property.and", "true");
        predicates.put("p.limit", "-1");

        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(
                PredicateGroup.create(predicates),
                session
        );
        SearchResult result = query.getResult();

        for (Hit hit : result.getHits()) {
           // taggedPages.add(hit.getPath());
            Resource pageResource = resourceResolver.getResource(hit.getPath());
            Page page = pageResource.adaptTo(Page.class);
            if (page != null) {
                String title = page.getTitle() != null ? page.getTitle() : page.getName();
                taggedPages.add(new PageInfo(page.getPath(), title));
            }
        }
    }
    public static class PageInfo {
        private final String path;
        private final String title;
        public PageInfo(String path, String title) {
            this.path = path;
            this.title = title;
        }
        public String getPath() {
            return path;
        }
        public String getTitle() {
            return title;
        }
    }
}
