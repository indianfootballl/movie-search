package com.moviesearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActorSearchRequest {
    private String actorName;
    private String language = "hi"; // Default to Hindi
    private int maxResults = 10;

    public ActorSearchRequest() {}

    public ActorSearchRequest(String actorName, String language) {
        this.actorName = actorName;
        this.language = language;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
