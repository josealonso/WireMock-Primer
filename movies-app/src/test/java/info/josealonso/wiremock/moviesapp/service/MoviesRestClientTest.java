package info.josealonso.wiremock.moviesapp.service;

import info.josealonso.wiremock.moviesapp.dto.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}

