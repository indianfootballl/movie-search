package com.moviesearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSearchRequest {
    private String query;
    private SearchType searchType;
    private String language;
    private int maxResults = 10;

    public enum SearchType {
        GENRE, ACTOR, LANGUAGE, GENERAL
    }

    public MovieSearchRequest() {}

    public MovieSearchRequest(String query, SearchType searchType) {
        this.query = query;
        this.searchType = searchType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
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
