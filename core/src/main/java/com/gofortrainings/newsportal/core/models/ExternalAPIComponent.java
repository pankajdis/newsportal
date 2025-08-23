package com.gofortrainings.newsportal.core.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExternalAPIComponent {
    @ValueMapValue
    private String apiUrl;


    public List<String> getApiData() throws IOException {
        apiData = fetchApiData(apiUrl);
        return apiData;
    }

    private List<String> apiData;

    public String getApiUrl() {
        return apiUrl;
    }

    /*@PostConstruct
    protected void init() throws IOException {
        apiData = fetchApiData(apiUrl);
    }
*/
    private List<String> fetchApiData(String apiUrl) throws IOException {
      //  List<String> data = new ArrayList<>();
        URL url = new URL(apiUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        if(httpURLConnection.getResponseCode()==200){
            BufferedReader bfr = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            /*String response = bfr.readLine();
            List<String> res = new ArrayList<>();*/
            // parse response into required res format then return res
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }

            String response = sb.toString();
            List<String> res = new ArrayList<>();

            // Parse using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // Top-level fields
            res.add("id: " + rootNode.path("id").asText());
            res.add("name: " + rootNode.path("name").asText());

            // Nested 'data' fields
            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null) {
                Iterator<String> fieldNames = dataNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    String value = dataNode.get(fieldName).asText();
                    res.add(fieldName + ": " + value);
                }
            }
            return res;
        }
        else {
            return List.of("No data fetched");
        }
    }
}