# Quick Start Scripts

## Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

## Frontend Setup
```bash
cd frontend
npm install
npm start
```

## Environment Variables
Create a `.env` file in the backend directory:
```
YOUTUBE_API_KEY=your_youtube_api_key_here
```

## Testing the API
```bash
# Test health endpoint
curl http://localhost:8080/api/health

# Test search endpoint
curl "http://localhost:8080/api/search?query=action&searchType=GENRE&maxResults=5"
```
