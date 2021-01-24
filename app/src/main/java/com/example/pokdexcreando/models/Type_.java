package com.example.pokdexcreando.models;

import java.util.HashMap;
import java.util.Map;

public class Type_ {
    private String name;
    private String url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
