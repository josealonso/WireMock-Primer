package info.josealonso.wiremock.moviesapp.service;

import info.josealonso.wiremock.moviesapp.constants.MoviesAppConstants;
import info.josealonso.wiremock.moviesapp.dto.Movie;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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
}
