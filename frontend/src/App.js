import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

function App() {
  const [currentView, setCurrentView] = useState('home'); // 'home', 'regions', 'actors', 'movies', 'movie-language'
  const [selectedRegion, setSelectedRegion] = useState(null);
  const [selectedActor, setSelectedActor] = useState(null);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [regions, setRegions] = useState([]);
  const [actors, setActors] = useState([]);
  const [movies, setMovies] = useState([]);
  const [movieResults, setMovieResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedLanguage, setSelectedLanguage] = useState('hi');

  const languages = [
    { code: 'hi', name: 'Hindi', flag: '🇮🇳' },
    { code: 'en', name: 'English', flag: '🇺🇸' },
    { code: 'ta', name: 'Tamil', flag: '🇮🇳' },
    { code: 'te', name: 'Telugu', flag: '🇮🇳' },
    { code: 'kn', name: 'Kannada', flag: '🇮🇳' },
    { code: 'ml', name: 'Malayalam', flag: '🇮🇳' },
    { code: 'bn', name: 'Bengali', flag: '🇮🇳' },
    { code: 'gu', name: 'Gujarati', flag: '🇮🇳' },
    { code: 'pa', name: 'Punjabi', flag: '🇮🇳' }
  ];

  useEffect(() => {
    if (currentView === 'regions') {
      fetchRegionalIndustries();
    }
  }, [currentView]);

  const fetchRegionalIndustries = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_BASE_URL}/regional-industries`);
      setRegions(response.data);
    } catch (err) {
      setError('Error fetching regional industries');
      console.error('Error fetching regions:', err);
    } finally {
      setLoading(false);
    }
  };

  const fetchActorsByRegion = async (region) => {
    setLoading(true);
    setError('');
    setSelectedRegion(region);
    setCurrentView('actors');

    try {
      const response = await axios.get(`${API_BASE_URL}/actors/${region.code}`);
      setActors(response.data);
    } catch (err) {
      setError('Error fetching actors for this region');
      console.error('Error fetching actors:', err);
    } finally {
      setLoading(false);
    }
  };

  const searchActors = async (query) => {
    if (!selectedRegion) return;
    
    try {
      const response = await axios.get(`${API_BASE_URL}/actors/${selectedRegion.code}/search`, {
        params: { query }
      });
      setActors(response.data);
    } catch (err) {
      console.error('Error searching actors:', err);
    }
  };

  const fetchMoviesByActor = async (actor) => {
    setLoading(true);
    setError('');
    setSelectedActor(actor);
    setCurrentView('movies');

    try {
      const response = await axios.get(`${API_BASE_URL}/search-by-actor`, {
        params: {
          actorName: actor.name,
          language: selectedLanguage,
          maxResults: 12
        }
      });
      setMovies(response.data);
    } catch (err) {
      setError('Error fetching movies for this actor');
      console.error('Error fetching movies:', err);
    } finally {
      setLoading(false);
    }
  };

  const searchMoviesByLanguage = async (movie, language) => {
    setLoading(true);
    setError('');
    setSelectedMovie(movie);
    setSelectedLanguage(language);
    setCurrentView('movie-language');

    try {
      const response = await axios.get(`${API_BASE_URL}/search-by-movie-language`, {
        params: {
          movieTitle: movie.title,
          actorName: selectedActor.name,
          preferredLanguage: language,
          maxResults: 8
        }
      });
      setMovieResults(response.data);
    } catch (err) {
      setError('Error fetching movie results');
      console.error('Error fetching movie results:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleMovieClick = (movie) => {
    setSelectedMovie(movie);
  };

  const closeModal = () => {
    setSelectedMovie(null);
  };

  const renderHomeView = () => (
    <div className="home-container">
      <div className="hero-section">
        <h1>🎬 Indian Movie Search</h1>
        <p>Discover movies by regional industries and actors</p>
        
        <div className="search-section">
          <div className="language-selector">
            <label>Default Language:</label>
            <select 
              value={selectedLanguage} 
              onChange={(e) => setSelectedLanguage(e.target.value)}
              className="language-select"
            >
              {languages.map(lang => (
                <option key={lang.code} value={lang.code}>
                  {lang.flag} {lang.name}
                </option>
              ))}
            </select>
          </div>
          
          <button 
            className="cta-button"
            onClick={() => setCurrentView('regions')}
          >
            Browse Regional Industries
          </button>
        </div>
      </div>

      <div className="features-section">
        <h2>How It Works</h2>
        <div className="flow-steps">
          <div className="step">
            <div className="step-number">1</div>
            <h3>Choose Region</h3>
            <p>Select from Bollywood, Tamil, Telugu, Kannada, or Malayalam cinema</p>
          </div>
          <div className="step">
            <div className="step-number">2</div>
            <h3>Pick Actor</h3>
            <p>Browse actors from your chosen regional industry</p>
          </div>
          <div className="step">
            <div className="step-number">3</div>
            <h3>Select Movie</h3>
            <p>View movies featuring your selected actor</p>
          </div>
          <div className="step">
            <div className="step-number">4</div>
            <h3>Choose Language</h3>
            <p>Select your preferred language to watch the movie</p>
          </div>
        </div>
      </div>
    </div>
  );

  const renderRegionsView = () => (
    <div className="regions-container">
      <div className="regions-header">
        <button className="back-button" onClick={() => setCurrentView('home')}>
          ← Back to Home
        </button>
        <h2>Regional Industries</h2>
        <p>Choose a regional film industry to explore actors</p>
      </div>

      {loading ? (
        <div className="loading">Loading regional industries...</div>
      ) : (
        <div className="regions-grid">
          {regions.map((region) => (
            <div
              key={region.code}
              className="region-card"
              onClick={() => fetchActorsByRegion(region)}
            >
              <div className="region-icon">{region.flag}</div>
              <div className="region-info">
                <h3>{region.name}</h3>
                <p>{region.description}</p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );

  const renderActorsView = () => (
    <div className="actors-container">
      <div className="actors-header">
        <button className="back-button" onClick={() => setCurrentView('regions')}>
          ← Back to Regions
        </button>
        <h2>{selectedRegion?.name} Actors</h2>
        
        <div className="actor-search">
          <input
            type="text"
            placeholder="Search actors..."
            value={searchQuery}
            onChange={(e) => {
              setSearchQuery(e.target.value);
              searchActors(e.target.value);
            }}
            className="actor-search-input"
          />
        </div>
      </div>

      {loading ? (
        <div className="loading">Loading actors...</div>
      ) : (
        <div className="actors-grid">
          {actors.map((actor) => (
            <div
              key={actor.name}
              className="actor-card"
              onClick={() => fetchMoviesByActor(actor)}
            >
              <img
                src={actor.imageUrl}
                alt={actor.name}
                className="actor-image"
              />
              <div className="actor-info">
                <h3>{actor.name}</h3>
                <p>{actor.description}</p>
                <div className="actor-languages">
                  {actor.languages.map(lang => (
                    <span key={lang} className="lang-tag">
                      {languages.find(l => l.code === lang)?.flag} {languages.find(l => l.code === lang)?.name}
                    </span>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );

  const renderMoviesView = () => (
    <div className="movies-container">
      <div className="movies-header">
        <button className="back-button" onClick={() => setCurrentView('actors')}>
          ← Back to Actors
        </button>
        <h2>Movies by {selectedActor?.name}</h2>
        
        <div className="language-selector">
          <label>Language:</label>
          <select 
            value={selectedLanguage} 
            onChange={(e) => {
              setSelectedLanguage(e.target.value);
              if (selectedActor) {
                fetchMoviesByActor(selectedActor);
              }
            }}
            className="language-select"
          >
            {languages.map(lang => (
              <option key={lang.code} value={lang.code}>
                {lang.flag} {lang.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      {loading ? (
        <div className="loading">Loading movies...</div>
      ) : movies.length > 0 ? (
        <div className="movies-grid">
          {movies.map((movie) => (
            <div
              key={movie.videoId}
              className="movie-card"
              onClick={() => searchMoviesByLanguage(movie, selectedLanguage)}
            >
              <img
                src={movie.thumbnailUrl}
                alt={movie.title}
                className="movie-thumbnail"
              />
              <div className="movie-info">
                <h3 className="movie-title">{movie.title}</h3>
                <p className="movie-channel">{movie.channelTitle}</p>
                <p className="movie-description">
                  {movie.description.substring(0, 100)}...
                </p>
                <div className="movie-action">
                  <span className="action-text">Click to choose language</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="no-results">
          <p>No movies found for {selectedActor?.name} in {languages.find(l => l.code === selectedLanguage)?.name}</p>
        </div>
      )}
    </div>
  );

  const renderMovieLanguageView = () => (
    <div className="movie-language-container">
      <div className="movie-language-header">
        <button className="back-button" onClick={() => setCurrentView('movies')}>
          ← Back to Movies
        </button>
        <h2>Language Options for "{selectedMovie?.title}"</h2>
        <p>Choose your preferred language to watch this movie</p>
      </div>

      <div className="language-options">
        {languages.map((lang) => (
          <div
            key={lang.code}
            className="language-option"
            onClick={() => searchMoviesByLanguage(selectedMovie, lang.code)}
          >
            <div className="language-flag">{lang.flag}</div>
            <div className="language-name">{lang.name}</div>
          </div>
        ))}
      </div>

      {loading ? (
        <div className="loading">Searching for movies...</div>
      ) : movieResults.length > 0 ? (
        <div className="movie-results">
          <h3>Available Movies in {languages.find(l => l.code === selectedLanguage)?.name}</h3>
          <div className="movies-grid">
            {movieResults.map((movie) => (
              <div
                key={movie.videoId}
                className="movie-card"
                onClick={() => handleMovieClick(movie)}
              >
                <img
                  src={movie.thumbnailUrl}
                  alt={movie.title}
                  className="movie-thumbnail"
                />
                <div className="movie-info">
                  <h3 className="movie-title">{movie.title}</h3>
                  <p className="movie-channel">{movie.channelTitle}</p>
                  <p className="movie-description">
                    {movie.description.substring(0, 100)}...
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="no-results">
          <p>No movies found in {languages.find(l => l.code === selectedLanguage)?.name}</p>
        </div>
      )}
    </div>
  );

  return (
    <div className="App">
      <header className="App-header">
        <h1>🎬 Indian Movie Search</h1>
        <p>Discover movies by regional industries and actors</p>
      </header>

      <main className="main-content">
        {error && <div className="error-message">{error}</div>}

        {currentView === 'home' && renderHomeView()}
        {currentView === 'regions' && renderRegionsView()}
        {currentView === 'actors' && renderActorsView()}
        {currentView === 'movies' && renderMoviesView()}
        {currentView === 'movie-language' && renderMovieLanguageView()}
      </main>

      {selectedMovie && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <button className="close-button" onClick={closeModal}>×</button>
            <h2>{selectedMovie.title}</h2>
            <div className="video-container">
              <iframe
                width="100%"
                height="400"
                src={`https://www.youtube.com/embed/${selectedMovie.videoId}`}
                title={selectedMovie.title}
                frameBorder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              ></iframe>
            </div>
            <div className="video-details">
              <p><strong>Channel:</strong> {selectedMovie.channelTitle}</p>
              <p><strong>Published:</strong> {new Date(selectedMovie.publishedAt).toLocaleDateString()}</p>
              <p><strong>Description:</strong></p>
              <p className="video-description">{selectedMovie.description}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;