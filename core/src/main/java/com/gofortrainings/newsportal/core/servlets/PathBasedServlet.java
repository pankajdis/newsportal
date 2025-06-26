package com.gofortrainings.newsportal.core.servlets;

import com.google.gson.JsonObject;
import org.apache.http.HttpConnection;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = Servlet.class,immediate = true)
@SlingServletPaths("/sample/test")
@ServiceDescription("Simple Demo Path Based Servlet")
public class PathBasedServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID=1L;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
      //  response.setContentType("application/json");
        PrintWriter out = response.getWriter();
      //  JsonObject responce = new JsonObject();
        String urlLink = "https://api.restful-api.dev/objects";
        StringBuffer sb = new StringBuffer();
        String line = "";
        URL url1 = new URL(urlLink);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.connect();
        int responceCode = httpURLConnection.getResponseCode();
      //  responce.addProperty("Status",responceCode);
        if(responceCode == 200){
            /*InputStream im = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(im);
            BufferedReader br = new BufferedReader(inputStreamReader);*/
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while((line= br.readLine())!=null){
                sb.append(line);
            }

        }
        //response.getWriter().print(sb);
        out.println(sb);
    }
}

