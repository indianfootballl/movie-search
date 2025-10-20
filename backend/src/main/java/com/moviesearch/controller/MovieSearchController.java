package com.moviesearch.controller;

import com.moviesearch.dto.MovieSearchRequest;
import com.moviesearch.dto.MovieSearchResponse;
import com.moviesearch.dto.ActorSearchRequest;
import com.moviesearch.dto.IndianActor;
import com.moviesearch.dto.RegionalIndustry;
import com.moviesearch.dto.MovieLanguageRequest;
import com.moviesearch.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MovieSearchController {

    @Autowired
    private YouTubeService youTubeService;

    @PostMapping("/search")
    public ResponseEntity<List<MovieSearchResponse>> searchMovies(@RequestBody MovieSearchRequest request) {
        try {
            List<MovieSearchResponse> results = youTubeService.searchMovies(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieSearchResponse>> searchMoviesGet(
            @RequestParam String query,
            @RequestParam(defaultValue = "GENERAL") MovieSearchRequest.SearchType searchType,
            @RequestParam(required = false) String language,
            @RequestParam(defaultValue = "10") int maxResults) {
        
        MovieSearchRequest request = new MovieSearchRequest(query, searchType);
        request.setLanguage(language);
        request.setMaxResults(maxResults);
        
        try {
            List<MovieSearchResponse> results = youTubeService.searchMovies(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAvailableGenres() {
        List<String> genres = List.of(
            "action", "comedy", "drama", "horror", "romance", "thriller",
            "sci-fi", "fantasy", "adventure", "animation", "documentary",
            "crime", "mystery", "war", "western"
        );
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/regional-industries")
    public ResponseEntity<List<RegionalIndustry>> getRegionalIndustries() {
        try {
            List<RegionalIndustry> industries = youTubeService.getRegionalIndustries();
            return ResponseEntity.ok(industries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/actors/{regionCode}")
    public ResponseEntity<List<IndianActor>> getActorsByRegion(@PathVariable String regionCode) {
        try {
            List<IndianActor> actors = youTubeService.getActorsByRegion(regionCode);
            return ResponseEntity.ok(actors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/actors/{regionCode}/search")
    public ResponseEntity<List<IndianActor>> searchActorsByRegion(
            @PathVariable String regionCode,
            @RequestParam(required = false) String query) {
        try {
            List<IndianActor> actors = youTubeService.searchActorsByRegion(regionCode, query);
            return ResponseEntity.ok(actors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/search-by-actor")
    public ResponseEntity<List<MovieSearchResponse>> searchMoviesByActor(@RequestBody ActorSearchRequest request) {
        try {
            List<MovieSearchResponse> results = youTubeService.searchMoviesByActor(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search-by-actor")
    public ResponseEntity<List<MovieSearchResponse>> searchMoviesByActorGet(
            @RequestParam String actorName,
            @RequestParam(defaultValue = "hi") String language,
            @RequestParam(defaultValue = "10") int maxResults) {
        
        ActorSearchRequest request = new ActorSearchRequest(actorName, language);
        request.setMaxResults(maxResults);
        
        try {
            List<MovieSearchResponse> results = youTubeService.searchMoviesByActor(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/search-by-movie-language")
    public ResponseEntity<List<MovieSearchResponse>> searchMoviesByTitleAndLanguage(@RequestBody MovieLanguageRequest request) {
        try {
            List<MovieSearchResponse> results = youTubeService.searchMoviesByTitleAndLanguage(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search-by-movie-language")
    public ResponseEntity<List<MovieSearchResponse>> searchMoviesByTitleAndLanguageGet(
            @RequestParam String movieTitle,
            @RequestParam String actorName,
            @RequestParam(defaultValue = "hi") String preferredLanguage,
            @RequestParam(defaultValue = "10") int maxResults) {
        
        MovieLanguageRequest request = new MovieLanguageRequest(movieTitle, actorName, preferredLanguage);
        request.setMaxResults(maxResults);
        
        try {
            List<MovieSearchResponse> results = youTubeService.searchMoviesByTitleAndLanguage(request);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Movie Search API is running!");
    }
}
