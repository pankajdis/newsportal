package com.gofortrainings.newsportal.core.models;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.adobe.cq.wcm.core.components.models.LayoutContainer;
import lombok.experimental.Delegate;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},adapters = {Carousel.class, CarouselTest.class}, resourceType = "newsportal/components/carouselTest",defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(
        name = "jackson",
        extensions ="json",
        selector = "geeks"
)
public class CarouselTestImpl implements CarouselTest, Carousel {

    @ValueMapValue
    private String title;

    @Override
    public String getTitle() {
        return title.toUpperCase();
    }


    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate(types=Carousel.class)
    private Carousel carousel;

    public Carousel getCarousel() {
        return carousel;
    }
}
