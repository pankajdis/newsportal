package com.gofortrainings.newsportal.core.servlets;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.google.gson.JsonElement;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import com.google.gson.JsonObject;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

@Component(
        service = { Servlet.class },
        property = {
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/readcfjson"
        }
)
public class ReadContentFragmentServlet  extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        ResourceResolver resolver = request.getResourceResolver();

        // 1️⃣ Get the CF resource (replace with your actual path)
        Resource cfResource = resolver.getResource("/content/dam/newsportal/contentfragment/test1");

        if (cfResource == null) {
            response.getWriter().write("{\"error\": \"Content Fragment not found\"}");
            return;
        }

        // 2️⃣ Adapt to ContentFragment
        ContentFragment contentFragment = cfResource.adaptTo(ContentFragment.class);

        if (contentFragment == null) {
            response.getWriter().write("{\"error\": \"Not a valid Content Fragment\"}");
            return;
        }

        JsonObject json;
        try {
            json = buildFragmentJson(contentFragment, resolver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


      /*  // 3️⃣ Extract a specific element (field) — e.g., title or author
        ContentElement titleElement = contentFragment.getElement("name"); // element name from CF model
        String titleValue = "";
        if (titleElement != null) {
            FragmentData data = titleElement.getValue();
            titleValue = data.getValue(String.class);
        }*/
        // 4️⃣ Build JSON response
        //    JSONObject jsonResponse = new JSONObject();



        /*for (Iterator<ContentElement> it = contentFragment.getElements(); it.hasNext(); ) {
            ContentElement element = it.next();
            String name = element.getName();
            String value = element.getValue().getValue(String.class);
            try {
                jsonResponse.put(name, value);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }*/

        /*try {
            jsonResponse.put("title", titleValue);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/

        // 5️⃣ Send response
        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }

    private JsonObject buildFragmentJson(ContentFragment fragment, ResourceResolver resolver) throws JSONException {
        JsonObject jsonObject = new JsonObject();

        for (Iterator<ContentElement> it = fragment.getElements(); it.hasNext(); ) {
            ContentElement element = it.next();
            String fieldName = element.getName();
            FragmentData data = element.getValue();

            // ⚡ Check if this element is a Fragment Reference
            if ("content-fragment".equalsIgnoreCase(data.getDataType().getSemanticType())) {
                // The value will be a path to the referenced fragment
                String referencedPath = data.getValue(String.class);
                Resource refResource = resolver.getResource(referencedPath);

                if (refResource != null) {
                    ContentFragment referencedFragment = refResource.adaptTo(ContentFragment.class);
                    if (referencedFragment != null) {
                        // 🔁 Recursively add referenced fragment data
                    //    jsonObject.put(fieldName, buildFragmentJson(referencedFragment, resolver));
                        jsonObject.add(fieldName,buildFragmentJson(referencedFragment,resolver));
                    }
                }
            } else {
                // 🧩 Regular element (text, number, etc.)
            //    jsonObject.put(fieldName, data.getValue(String.class));
                jsonObject.add(fieldName, (JsonElement) data.getValue());
            }
        }

        return jsonObject;
    }

}
