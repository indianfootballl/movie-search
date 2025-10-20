# Movie Search AI - Development Guide

## Project Overview
This is a full-stack movie search application that uses YouTube's Data API to find and display movies based on genre, actor, or language searches.

## Architecture

### Backend (Spring Boot)
- **Port**: 8080
- **Main Class**: `MovieSearchApplication.java`
- **Key Components**:
  - `MovieSearchController`: REST API endpoints
  - `YouTubeService`: YouTube API integration with caching
  - `MovieSearchRequest/Response`: DTOs for API communication

### Frontend (React)
- **Port**: 3000
- **Main Component**: `App.js`
- **Features**:
  - Search form with type selection
  - Movie grid display
  - Modal player for YouTube videos
  - Responsive design

## Key Features Implemented

### 1. Search Types
- **Genre**: Predefined genre mapping to YouTube search terms
- **Actor**: Direct actor name search
- **Language**: Language-specific movie search
- **General**: General movie search

### 2. YouTube Integration
- Uses YouTube Data API v3 search endpoint
- Filters for long videos in Movies category
- Caches results to optimize API quota usage
- Returns video metadata (title, description, thumbnail, etc.)

### 3. User Interface
- Modern gradient design with glassmorphism effects
- Responsive grid layout for movie cards
- Modal popup for video playback
- Search type-specific UI elements

## Development Notes

### API Quotas
- YouTube API has daily quotas (10,000 units free)
- Search requests cost 100 units each
- Implemented caching to reduce API calls
- Consider implementing result pagination for production

### CORS Configuration
- Backend configured for `http://localhost:3000`
- Update CORS settings if changing frontend URL

### Error Handling
- Backend returns empty list on API errors
- Frontend displays user-friendly error messages
- Graceful fallback for missing thumbnails

## Future Enhancements

1. **User Authentication**: Add user accounts and favorites
2. **Advanced Filters**: Duration, year, rating filters
3. **Recommendations**: ML-based movie recommendations
4. **Offline Support**: PWA capabilities
5. **Social Features**: Share and rate movies
6. **Multiple APIs**: Integrate with other movie databases
7. **Analytics**: Track search patterns and popular movies

## Testing

### Manual Testing
1. Test all search types (genre, actor, language)
2. Verify YouTube video playback
3. Test responsive design on different screen sizes
4. Check error handling with invalid API key

### API Testing
```bash
# Test different search types
curl "http://localhost:8080/api/search?query=comedy&searchType=GENRE"
curl "http://localhost:8080/api/search?query=Tom%20Hanks&searchType=ACTOR"
curl "http://localhost:8080/api/search?query=movie&searchType=LANGUAGE&language=hi"
```

## Deployment Considerations

1. **Environment Variables**: Use environment variables for API keys
2. **HTTPS**: Enable HTTPS for production
3. **Rate Limiting**: Implement rate limiting for API endpoints
4. **Monitoring**: Add application monitoring and logging
5. **Scaling**: Consider horizontal scaling for high traffic
6. **CDN**: Use CDN for static assets

## Security Notes

1. **API Key Protection**: Never expose API keys in frontend code
2. **Input Validation**: Validate all user inputs
3. **CORS**: Configure CORS properly for production
4. **HTTPS**: Use HTTPS in production
5. **Rate Limiting**: Implement rate limiting to prevent abuse
