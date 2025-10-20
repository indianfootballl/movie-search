package com.moviesearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndianActor {
    private String name;
    private String imageUrl;
    private String description;
    private String[] languages;

    public IndianActor() {}

    public IndianActor(String name, String imageUrl, String description, String[] languages) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }
}
