package info.josealonso.wiremock.moviesapp.service;

import info.josealonso.wiremock.moviesapp.constants.MoviesAppConstants;
import info.josealonso.wiremock.moviesapp.dto.Movie;
import info.josealonso.wiremock.moviesapp.exception.MovieErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

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

    public List<Movie> retrieveMovieByName(String movieName) {
        String retrieveByNameUri = UriComponentsBuilder.fromUriString(MoviesAppConstants.MOVIE_BY_NAME_QUERY_PARAM)
                .queryParam("movie_name", movieName)
                .buildAndExpand()
                .toUriString();

        try {
            return webClient.get().uri(retrieveByNameUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieByName. Status code is {} " +
                    "and the message is {} ", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieByName and the message is {}", ex.getMessage());
            throw new MovieErrorResponse(ex);
        }
    }

    public List<Movie> retrieveMovieByYear(Integer movieYear) {
        String retrieveByNameUri = UriComponentsBuilder.fromUriString(MoviesAppConstants.MOVIE_BY_YEAR_QUERY_PARAM)
                .queryParam("year", movieYear)
                .buildAndExpand()
                .toUriString();

        try {
            return webClient.get().uri(retrieveByNameUri)
                    .retrieve()
                    .bodyToFlux(Movie.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException in retrieveMovieByYear. Status code is {} " +
                    "and the message is {} ", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new MovieErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception in retrieveMovieByYear and the message is {}", ex.getMessage());
            throw new MovieErrorResponse(ex);
        }
    }

}
