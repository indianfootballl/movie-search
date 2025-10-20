# Indian Movie Search API - Curl Commands

## 1. Get All Indian Actors
curl http://localhost:8080/api/indian-actors

## 2. Search Indian Actors
curl "http://localhost:8080/api/indian-actors/search?query=Shah"

## 3. Search Movies by Actor (Hindi - Default)
curl "http://localhost:8080/api/search-by-actor?actorName=Shah%20Rukh%20Khan&language=hi&maxResults=5"

## 4. Search Movies by Actor (Tamil)
curl "http://localhost:8080/api/search-by-actor?actorName=Rajinikanth&language=ta&maxResults=5"

## 5. Search Movies by Actor (Telugu)
curl "http://localhost:8080/api/search-by-actor?actorName=Prabhas&language=te&maxResults=5"

## 6. Search Movies by Actor (Malayalam)
curl "http://localhost:8080/api/search-by-actor?actorName=Mammootty&language=ml&maxResults=5"

## 7. Search Movies by Actor (Kannada)
curl "http://localhost:8080/api/search-by-actor?actorName=Yash&language=kn&maxResults=5"

## 8. POST Request for Actor Search
curl -X POST http://localhost:8080/api/search-by-actor \
  -H "Content-Type: application/json" \
  -d '{
    "actorName": "Amitabh Bachchan",
    "language": "hi",
    "maxResults": 5
  }'

## 9. Test Health Endpoint
curl http://localhost:8080/api/health

## 10. Test Original Search (Still Available)
curl "http://localhost:8080/api/search?query=action&searchType=GENRE&maxResults=3"
