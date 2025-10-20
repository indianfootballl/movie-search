# Movie Search AI

A modern web application that allows users to search for movies by genre, actor, or language using the YouTube Data API. Built with Spring Boot backend and React frontend.

## Features

- 🎬 **Genre Search**: Search movies by genre (action, comedy, drama, horror, etc.)
- 👤 **Actor Search**: Find movies featuring specific actors
- 🌍 **Language Search**: Search for movies in different languages
- 🎥 **YouTube Integration**: Direct playback of movies using YouTube embed
- 📱 **Responsive Design**: Works on desktop and mobile devices
- ⚡ **Fast Search**: Cached results for better performance

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring WebFlux** (for reactive HTTP client)
- **Spring Cache** (for result caching)
- **Maven** (dependency management)

### Frontend
- **React 18**
- **Axios** (HTTP client)
- **CSS3** (modern styling with gradients and animations)

## Prerequisites

Before running the application, make sure you have:

1. **Java 17** or higher installed
2. **Node.js 16** or higher installed
3. **Maven 3.6** or higher installed
4. **YouTube Data API v3** API key

## Setup Instructions

### 1. Get YouTube API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the **YouTube Data API v3**
4. Go to **Credentials** → **Create Credentials** → **API Key**
5. Copy your API key

### 2. Configure Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Update the API key in `src/main/resources/application.properties`:
   ```properties
   YOUTUBE_API_KEY=your_actual_api_key_here
   ```

3. Build and run the backend:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### 3. Configure Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:3000`

## Usage

1. Open your browser and go to `http://localhost:3000`
2. Choose your search type:
   - **General**: Search for any movie
   - **Genre**: Select from predefined genres
   - **Actor**: Search by actor name
   - **Language**: Search by language code (e.g., en, es, fr, hi)
3. Enter your search term
4. Click "Search" to find movies
5. Click on any movie card to watch it in the embedded player

## API Endpoints

### Backend API

- `GET /api/search` - Search for movies
  - Parameters: `query`, `searchType`, `language`, `maxResults`
- `GET /api/genres` - Get available genres
- `GET /api/health` - Health check

### Example API Calls

```bash
# Search for action movies
curl "http://localhost:8080/api/search?query=action&searchType=GENRE&maxResults=5"

# Search for movies by actor
curl "http://localhost:8080/api/search?query=Tom%20Hanks&searchType=ACTOR&maxResults=5"

# Search for Hindi movies
curl "http://localhost:8080/api/search?query=movie&searchType=LANGUAGE&language=hi&maxResults=5"
```

## Project Structure

```
movie-search-ai/
├── backend/
│   ├── src/main/java/com/moviesearch/
│   │   ├── MovieSearchApplication.java
│   │   ├── controller/
│   │   │   └── MovieSearchController.java
│   │   ├── dto/
│   │   │   ├── MovieSearchRequest.java
│   │   │   └── MovieSearchResponse.java
│   │   └── service/
│   │       └── YouTubeService.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   ├── public/
│   │   ├── index.html
│   │   └── manifest.json
│   └── package.json
└── README.md
```

## Configuration

### Backend Configuration

Key configuration options in `application.properties`:

```properties
# YouTube API Key (REQUIRED)
YOUTUBE_API_KEY=your_youtube_api_key_here

# Server Configuration
server.port=8080

# CORS Configuration
cors.allowed-origins=http://localhost:3000

# Cache Configuration
spring.cache.type=simple
spring.cache.cache-names=youtube-search
```

### Frontend Configuration

The frontend automatically proxies API requests to the backend. If you change the backend port, update the proxy in `package.json`:

```json
{
  "proxy": "http://localhost:8080"
}
```

## Troubleshooting

### Common Issues

1. **"API key not valid" error**
   - Make sure your YouTube API key is correct
   - Ensure YouTube Data API v3 is enabled in Google Cloud Console

2. **CORS errors**
   - Backend CORS is configured for `http://localhost:3000`
   - If using a different frontend URL, update CORS settings

3. **No search results**
   - YouTube API has quota limits (100 units per day for free)
   - Try different search terms
   - Check if the search query is too specific

4. **Backend won't start**
   - Ensure Java 17+ is installed
   - Check if port 8080 is available
   - Verify Maven dependencies are downloaded

### YouTube API Quotas

- **Free tier**: 10,000 units per day
- **Search request**: 100 units
- **Video details**: 1 unit per video

To optimize quota usage:
- Results are cached for repeated searches
- Use specific search terms
- Limit `maxResults` parameter

## Development

### Adding New Genres

To add new genres, update the `GENRE_KEYWORDS` map in `YouTubeService.java`:

```java
private static final Map<String, String> GENRE_KEYWORDS = Map.of(
    "action", "action movie full movie",
    "comedy", "comedy movie full movie",
    // Add your new genre here
    "musical", "musical movie full movie"
);
```

### Customizing Search Logic

Modify the `buildSearchQuery` method in `YouTubeService.java` to customize how search queries are constructed.

### Styling Changes

The frontend uses modern CSS with:
- CSS Grid for movie layout
- Flexbox for form layout
- CSS gradients and backdrop filters
- Responsive design with media queries

## License

This project is open source and available under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## Support

## Deployment

### Fly.io

Prerequisites:
- Install `flyctl` and login.
- Export your YouTube API key: `export YOUTUBE_API_KEY=...`

Deploy both backend and frontend:

```bash
./deploy-to-fly.sh
```

Custom app names:

```bash
./deploy-to-fly.sh my-backend-app my-frontend-app
```

The script:
- Creates apps if missing
- Sets backend secret `YOUTUBE_API_KEY`
- Builds/deploys backend (Java on port 8080)
- Builds/deploys frontend with `REACT_APP_API_URL` pointing to backend `/api`
- Sets backend CORS to allow the frontend origin

If you encounter any issues or have questions, please:
1. Check the troubleshooting section
2. Review the YouTube API documentation
3. Create an issue in the repository
