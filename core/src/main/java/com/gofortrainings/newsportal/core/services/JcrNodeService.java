package com.gofortrainings.newsportal.core.services;

import javax.jcr.RepositoryException;

public interface JcrNodeService {

    public String updateNode(String path, String propertyName, String propertyValue) throws RepositoryException;
}
