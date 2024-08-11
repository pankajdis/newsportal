
package com.gofortrainings.newsportal.core.services;

        import org.apache.http.HttpEntity;
        import org.apache.http.client.methods.CloseableHttpResponse;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.CloseableHttpClient;
        import org.apache.http.impl.client.HttpClients;
        import org.apache.http.util.EntityUtils;
        import org.osgi.service.component.annotations.Activate;
        import org.osgi.service.component.annotations.Component;
        import org.osgi.service.component.annotations.Deactivate;
        import org.osgi.service.component.annotations.Modified;
        import org.osgi.service.metatype.annotations.Designate;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.io.IOException;

@Component(service = ArticleService.class,immediate = true)
@Designate(ocd = ArticleConfiguration.class)
public class ArticleService{

    private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

    private  String articleRestUrl;
    private boolean status;
    @Activate
    public void activate(ArticleConfiguration config){
        LOG.info("ArticleService - Inside Article Service Activate method");
        this.articleRestUrl = config.articleRestUrl();
        this.status = config.status();
    }

    @Deactivate
    public void deactivate(){
        LOG.info("ArticleService - Inside Deactivate Method");
    }

    @Modified
    public void modified(ArticleConfiguration config){
        LOG.info("ArticleService - Inside Modified Method");
        this.articleRestUrl = config.articleRestUrl();
        this.status = config.status();
    }

    public String getArticles(){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(articleRestUrl);
        String result =null;
        try{
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                result =    EntityUtils.toString(entity);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}