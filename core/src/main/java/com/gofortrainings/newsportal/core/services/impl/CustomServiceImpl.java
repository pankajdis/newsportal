package com.gofortrainings.newsportal.core.services.impl;

import com.gofortrainings.newsportal.core.services.CustomService;

public class CustomServiceImpl implements CustomService {

    @Override
    public String getCustomMessage() {
        return "Hello from OSGi Service!";
    }
}
