package info.josealonso.wiremock.moviesapp.service;

import info.josealonso.wiremock.moviesapp.constants.MoviesAppConstants;
import info.josealonso.wiremock.moviesapp.dto.Movie;
import info.josealonso.wiremock.moviesapp.exception.MovieErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class MoviesRestClient {

    private final WebClient webClient;

    public List<Movie> retrieveAllMovies() {
        return webClient.get().uri(MoviesAppConstants.GET_ALL_MOVIES_V1)
                .retrieve()
                .bodyToFlux(Movie.class)
                .collectList()
                .block();
    }

    public Movie retrieveMovieById(Integer movieId) {
        try {
            return webClient.get().uri(MoviesAppConstants.GET_MOVIE_BY_ID, movieId)
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieById. Status code is {} " +
                    "and the message is {} ", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieById and the message is {}", ex.getMessage());
            throw new MovieErrorResponse(ex);
        }
    }
}
