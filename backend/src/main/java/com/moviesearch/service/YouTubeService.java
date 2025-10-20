package com.moviesearch.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviesearch.dto.MovieSearchRequest;
import com.moviesearch.dto.MovieSearchResponse;
import com.moviesearch.dto.ActorSearchRequest;
import com.moviesearch.dto.IndianActor;
import com.moviesearch.dto.RegionalIndustry;
import com.moviesearch.dto.MovieLanguageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class YouTubeService {

    private final WebClient webClient;
    private final String apiKey;

    // Genre to search keyword mapping
    private static final Map<String, String> GENRE_KEYWORDS = Map.ofEntries(
        Map.entry("action", "action movie full movie"),
        Map.entry("comedy", "comedy movie full movie"),
        Map.entry("drama", "drama movie full movie"),
        Map.entry("horror", "horror movie full movie"),
        Map.entry("romance", "romance movie full movie"),
        Map.entry("thriller", "thriller movie full movie"),
        Map.entry("sci-fi", "science fiction movie full movie"),
        Map.entry("fantasy", "fantasy movie full movie"),
        Map.entry("adventure", "adventure movie full movie"),
        Map.entry("animation", "animated movie full movie"),
        Map.entry("documentary", "documentary movie full movie"),
        Map.entry("crime", "crime movie full movie"),
        Map.entry("mystery", "mystery movie full movie"),
        Map.entry("war", "war movie full movie"),
        Map.entry("western", "western movie full movie")
    );

    // Regional industries
    private static final List<RegionalIndustry> REGIONAL_INDUSTRIES = List.of(
        new RegionalIndustry("bollywood", "Bollywood", "🎬", "Hindi film industry"),
        new RegionalIndustry("tamil", "Tamil Cinema", "🎭", "Tamil film industry (Kollywood)"),
        new RegionalIndustry("telugu", "Telugu Cinema", "🎪", "Telugu film industry (Tollywood)"),
        new RegionalIndustry("kannada", "Kannada Cinema", "🎨", "Kannada film industry (Sandalwood)"),
        new RegionalIndustry("malayalam", "Malayalam Cinema", "🎪", "Malayalam film industry (Mollywood)")
    );

    // Actors organized by regional industry
    private static final Map<String, List<IndianActor>> ACTORS_BY_REGION = Map.ofEntries(
        Map.entry("bollywood", List.of(
            new IndianActor("Amitabh Bachchan", "https://via.placeholder.com/150", "Legendary Bollywood actor", new String[]{"hi", "en"}),
            new IndianActor("Shah Rukh Khan", "https://via.placeholder.com/150", "King of Bollywood", new String[]{"hi", "en"}),
            new IndianActor("Salman Khan", "https://via.placeholder.com/150", "Bollywood superstar", new String[]{"hi", "en"}),
            new IndianActor("Aamir Khan", "https://via.placeholder.com/150", "Perfectionist actor", new String[]{"hi", "en"}),
            new IndianActor("Hrithik Roshan", "https://via.placeholder.com/150", "Greek God of Bollywood", new String[]{"hi", "en"}),
            new IndianActor("Ranbir Kapoor", "https://via.placeholder.com/150", "Versatile actor", new String[]{"hi", "en"}),
            new IndianActor("Ranveer Singh", "https://via.placeholder.com/150", "Energetic performer", new String[]{"hi", "en"}),
            new IndianActor("Akshay Kumar", "https://via.placeholder.com/150", "Khiladi of Bollywood", new String[]{"hi", "en"}),
            new IndianActor("Ajay Devgn", "https://via.placeholder.com/150", "Action hero", new String[]{"hi", "en"}),
            new IndianActor("Varun Dhawan", "https://via.placeholder.com/150", "Young Bollywood star", new String[]{"hi", "en"})
        )),
        Map.entry("tamil", List.of(
            new IndianActor("Rajinikanth", "https://via.placeholder.com/150", "Tamil superstar", new String[]{"ta", "hi", "en"}),
            new IndianActor("Vijay", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"}),
            new IndianActor("Ajith Kumar", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"}),
            new IndianActor("Kamal Haasan", "https://via.placeholder.com/150", "Tamil legend", new String[]{"ta", "hi", "en"}),
            new IndianActor("Suriya", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"}),
            new IndianActor("Dhanush", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"}),
            new IndianActor("Vikram", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"}),
            new IndianActor("Sivakarthikeyan", "https://via.placeholder.com/150", "Tamil actor", new String[]{"ta", "hi"})
        )),
        Map.entry("telugu", List.of(
            new IndianActor("Prabhas", "https://via.placeholder.com/150", "Telugu superstar", new String[]{"te", "hi", "en"}),
            new IndianActor("Mahesh Babu", "https://via.placeholder.com/150", "Telugu actor", new String[]{"te", "hi"}),
            new IndianActor("Allu Arjun", "https://via.placeholder.com/150", "Stylish star", new String[]{"te", "hi"}),
            new IndianActor("Ram Charan", "https://via.placeholder.com/150", "Telugu actor", new String[]{"te", "hi"}),
            new IndianActor("NTR Jr", "https://via.placeholder.com/150", "Telugu actor", new String[]{"te", "hi"}),
            new IndianActor("Pawan Kalyan", "https://via.placeholder.com/150", "Telugu actor", new String[]{"te", "hi"}),
            new IndianActor("Chiranjeevi", "https://via.placeholder.com/150", "Telugu legend", new String[]{"te", "hi"}),
            new IndianActor("Vijay Deverakonda", "https://via.placeholder.com/150", "Telugu actor", new String[]{"te", "hi"})
        )),
        Map.entry("kannada", List.of(
            new IndianActor("Yash", "https://via.placeholder.com/150", "Kannada superstar", new String[]{"kn", "hi"}),
            new IndianActor("Sudeep", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Darshan", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Puneeth Rajkumar", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Shivarajkumar", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Rakshit Shetty", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Ganesh", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"}),
            new IndianActor("Dhruva Sarja", "https://via.placeholder.com/150", "Kannada actor", new String[]{"kn", "hi"})
        )),
        Map.entry("malayalam", List.of(
            new IndianActor("Mammootty", "https://via.placeholder.com/150", "Malayalam legend", new String[]{"ml", "hi", "en"}),
            new IndianActor("Mohanlal", "https://via.placeholder.com/150", "Malayalam superstar", new String[]{"ml", "hi", "en"}),
            new IndianActor("Dulquer Salmaan", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi", "en"}),
            new IndianActor("Fahadh Faasil", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi", "en"}),
            new IndianActor("Prithviraj Sukumaran", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi", "en"}),
            new IndianActor("Nivin Pauly", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi"}),
            new IndianActor("Tovino Thomas", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi"}),
            new IndianActor("Jayasurya", "https://via.placeholder.com/150", "Malayalam actor", new String[]{"ml", "hi"})
        ))
    );

    public YouTubeService(@Value("${YOUTUBE_API_KEY:}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com/youtube/v3")
                .build();
    }

    @Cacheable(value = "youtube-search", key = "#request.query + '_' + #request.searchType + '_' + #request.language")
    public List<MovieSearchResponse> searchMovies(MovieSearchRequest request) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("YouTube API key is not configured!");
            return Collections.emptyList();
        }
        
        String searchQuery = buildSearchQuery(request);
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("q", searchQuery)
                        .queryParam("type", "video")
                        .queryParam("maxResults", request.getMaxResults())
                        .queryParam("key", apiKey)
                        .queryParam("relevanceLanguage", request.getLanguage() != null ? request.getLanguage() : "en")
                        .queryParam("videoDuration", "long")
                        .queryParam("videoCategoryId", "10") // Movies category
                        .build())
                .retrieve()
                .bodyToMono(YouTubeSearchResponse.class)
                .map(response -> response.items.stream()
                        .map(this::convertToMovieResponse)
                        .collect(Collectors.toList()))
                .onErrorReturn(Collections.emptyList())
                .block();
    }

    @Cacheable(value = "youtube-search", key = "#request.actorName + '_' + #request.language")
    public List<MovieSearchResponse> searchMoviesByActor(ActorSearchRequest request) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("YouTube API key is not configured!");
            return Collections.emptyList();
        }
        
        String searchQuery = request.getActorName() + " movie full movie";
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("q", searchQuery)
                        .queryParam("type", "video")
                        .queryParam("maxResults", request.getMaxResults())
                        .queryParam("key", apiKey)
                        .queryParam("relevanceLanguage", request.getLanguage())
                        .queryParam("videoDuration", "long")
                        .queryParam("videoCategoryId", "10") // Movies category
                        .build())
                .retrieve()
                .bodyToMono(YouTubeSearchResponse.class)
                .map(response -> response.items.stream()
                        .map(this::convertToMovieResponse)
                        .collect(Collectors.toList()))
                .onErrorReturn(Collections.emptyList())
                .block();
    }

    public List<RegionalIndustry> getRegionalIndustries() {
        return REGIONAL_INDUSTRIES;
    }

    public List<IndianActor> getActorsByRegion(String regionCode) {
        return ACTORS_BY_REGION.getOrDefault(regionCode, Collections.emptyList());
    }

    public List<IndianActor> searchActorsByRegion(String regionCode, String query) {
        List<IndianActor> actors = ACTORS_BY_REGION.getOrDefault(regionCode, Collections.emptyList());
        
        if (query == null || query.trim().isEmpty()) {
            return actors;
        }
        
        String lowerQuery = query.toLowerCase();
        return actors.stream()
                .filter(actor -> actor.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "youtube-search", key = "#request.movieTitle + '_' + #request.actorName + '_' + #request.preferredLanguage")
    public List<MovieSearchResponse> searchMoviesByTitleAndLanguage(MovieLanguageRequest request) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("YouTube API key is not configured!");
            return Collections.emptyList();
        }
        
        String searchQuery = request.getMovieTitle() + " " + request.getActorName() + " movie full movie";
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("q", searchQuery)
                        .queryParam("type", "video")
                        .queryParam("maxResults", request.getMaxResults())
                        .queryParam("key", apiKey)
                        .queryParam("relevanceLanguage", request.getPreferredLanguage())
                        .queryParam("videoDuration", "long")
                        .queryParam("videoCategoryId", "10") // Movies category
                        .build())
                .retrieve()
                .bodyToMono(YouTubeSearchResponse.class)
                .map(response -> response.items.stream()
                        .map(this::convertToMovieResponse)
                        .collect(Collectors.toList()))
                .onErrorReturn(Collections.emptyList())
                .block();
    }

    private String buildSearchQuery(MovieSearchRequest request) {
        String query = request.getQuery().toLowerCase();
        
        switch (request.getSearchType()) {
            case GENRE:
                String genreKeywords = GENRE_KEYWORDS.getOrDefault(query, query + " movie full movie");
                return genreKeywords;
            case ACTOR:
                return query + " movie full movie";
            case LANGUAGE:
                return "movie full movie " + query;
            case GENERAL:
            default:
                return query + " movie full movie";
        }
    }

    private MovieSearchResponse convertToMovieResponse(YouTubeItem item) {
        MovieSearchResponse response = new MovieSearchResponse();
        response.setVideoId(item.id.videoId);
        response.setTitle(item.snippet.title);
        response.setDescription(item.snippet.description);
        response.setThumbnailUrl(item.snippet.thumbnails.medium.url);
        response.setChannelTitle(item.snippet.channelTitle);
        response.setPublishedAt(item.snippet.publishedAt);
        return response;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class YouTubeSearchResponse {
        @JsonProperty("items")
        public List<YouTubeItem> items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class YouTubeItem {
        @JsonProperty("id")
        public VideoId id;
        
        @JsonProperty("snippet")
        public Snippet snippet;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class VideoId {
        @JsonProperty("videoId")
        public String videoId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Snippet {
        @JsonProperty("title")
        public String title;
        
        @JsonProperty("description")
        public String description;
        
        @JsonProperty("channelTitle")
        public String channelTitle;
        
        @JsonProperty("publishedAt")
        public String publishedAt;
        
        @JsonProperty("thumbnails")
        public Thumbnails thumbnails;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Thumbnails {
        @JsonProperty("medium")
        public Thumbnail medium;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Thumbnail {
        @JsonProperty("url")
        public String url;
    }
}
