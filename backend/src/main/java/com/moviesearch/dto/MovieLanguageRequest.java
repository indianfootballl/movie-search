package com.moviesearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieLanguageRequest {
    private String movieTitle;
    private String actorName;
    private String preferredLanguage;
    private int maxResults = 10;

    public MovieLanguageRequest() {}

    public MovieLanguageRequest(String movieTitle, String actorName, String preferredLanguage) {
        this.movieTitle = movieTitle;
        this.actorName = actorName;
        this.preferredLanguage = preferredLanguage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
