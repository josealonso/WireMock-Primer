package info.josealonso.wiremock.moviesapp.service;

import info.josealonso.wiremock.moviesapp.dto.Movie;
import info.josealonso.wiremock.moviesapp.exception.MovieErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviesRestClientTest {

    public static final String BASE_URL = "http://localhost:8081";
    MoviesRestClient moviesRestClient;
    WebClient webClient;

    @BeforeEach
    void setUp() {
        String baseUrl = BASE_URL;
        webClient = WebClient.create(baseUrl);
        moviesRestClient = new MoviesRestClient(webClient);
    }

    @Test
    void retrieveAllMovies() {
        // when
        List<Movie> moviesList = moviesRestClient.retrieveAllMovies();

        // then
        assertTrue(moviesList.size() > 0);
    }

    @Test
    void retrieveMovieById() {
        Integer movieId = 1;
        Movie movie = moviesRestClient.retrieveMovieById(movieId);
        assertEquals("Batman Begins", movie.getName());
    }

    @Test
    void retrieveMovieById_notFound() {
        Integer movieId = 100;
        Movie movie = moviesRestClient.retrieveMovieById(movieId);
        assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieById(movieId));
    }

    @Test
    void retrieveMovieByName() {
        String movieName = "Avengers";
        List<Movie> movieList = moviesRestClient.retrieveMovieByName(movieName);
        String castExpected = "Robert Downey Jr, Chris Evans , Chris HemsWorth";
        assertEquals(castExpected, movieList.get(0).getCast());
    }

    @Test
    void retrieveMovieByName_notFound() {
        String movieName = "ABC";
        assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieByName(movieName));
    }

    @Test
    void retrieveMovieByYear() {
        Integer movieYear = 2012;
        List<Movie> movieList = moviesRestClient.retrieveMovieByYear(movieYear);
        assertEquals(2, movieList.size());
    }

    @Test
    void retrieveMovieByYear_not_found() {
        Integer movieYear = 1950;
        assertThrows(MovieErrorResponse.class, () -> moviesRestClient.retrieveMovieByYear(movieYear));
    }

}











