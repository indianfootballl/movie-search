# Hierarchical Movie Search API - Curl Commands

## 1. Get Regional Industries
curl http://localhost:8080/api/regional-industries

## 2. Get Actors by Region

### Bollywood Actors
curl http://localhost:8080/api/actors/bollywood

### Tamil Actors
curl http://localhost:8080/api/actors/tamil

### Telugu Actors
curl http://localhost:8080/api/actors/telugu

### Kannada Actors
curl http://localhost:8080/api/actors/kannada

### Malayalam Actors
curl http://localhost:8080/api/actors/malayalam

## 3. Search Actors by Region

### Search Bollywood Actors
curl "http://localhost:8080/api/actors/bollywood/search?query=Shah"

### Search Tamil Actors
curl "http://localhost:8080/api/actors/tamil/search?query=Rajini"

### Search Telugu Actors
curl "http://localhost:8080/api/actors/telugu/search?query=Prabhas"

### Search Kannada Actors
curl "http://localhost:8080/api/actors/kannada/search?query=Yash"

### Search Malayalam Actors
curl "http://localhost:8080/api/actors/malayalam/search?query=Mammootty"

## 4. Search Movies by Actor (Original Method)

### Shah Rukh Khan Movies (Hindi)
curl "http://localhost:8080/api/search-by-actor?actorName=Shah%20Rukh%20Khan&language=hi&maxResults=5"

### Rajinikanth Movies (Tamil)
curl "http://localhost:8080/api/search-by-actor?actorName=Rajinikanth&language=ta&maxResults=5"

### Prabhas Movies (Telugu)
curl "http://localhost:8080/api/search-by-actor?actorName=Prabhas&language=te&maxResults=5"

### Yash Movies (Kannada)
curl "http://localhost:8080/api/search-by-actor?actorName=Yash&language=kn&maxResults=5"

### Mammootty Movies (Malayalam)
curl "http://localhost:8080/api/search-by-actor?actorName=Mammootty&language=ml&maxResults=5"

## 5. Search Movies by Title and Language (New Method)

### Search specific movie with actor and language
curl "http://localhost:8080/api/search-by-movie-language?movieTitle=Baahubali&actorName=Prabhas&preferredLanguage=te&maxResults=5"

### Search movie in different languages
curl "http://localhost:8080/api/search-by-movie-language?movieTitle=Dangal&actorName=Aamir%20Khan&preferredLanguage=hi&maxResults=5"

curl "http://localhost:8080/api/search-by-movie-language?movieTitle=Dangal&actorName=Aamir%20Khan&preferredLanguage=en&maxResults=5"

## 6. POST Request for Movie Language Search
curl -X POST http://localhost:8080/api/search-by-movie-language \
  -H "Content-Type: application/json" \
  -d '{
    "movieTitle": "Baahubali",
    "actorName": "Prabhas",
    "preferredLanguage": "te",
    "maxResults": 5
  }'

## 7. Test Health Endpoint
curl http://localhost:8080/api/health

## 8. Test Original Search (Still Available)
curl "http://localhost:8080/api/search?query=action&searchType=GENRE&maxResults=3"

## Example Flow:

### Step 1: Get Regional Industries
curl http://localhost:8080/api/regional-industries

### Step 2: Get Malayalam Actors
curl http://localhost:8080/api/actors/malayalam

### Step 3: Search Movies by Mammootty
curl "http://localhost:8080/api/search-by-actor?actorName=Mammootty&language=ml&maxResults=5"

### Step 4: Search specific movie in different language
curl "http://localhost:8080/api/search-by-movie-language?movieTitle=Drishyam&actorName=Mammootty&preferredLanguage=hi&maxResults=5"
