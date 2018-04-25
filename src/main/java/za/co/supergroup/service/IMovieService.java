package za.co.supergroup.service;

import java.util.List;

import za.co.supergroup.model.jpa.Movie;

public interface IMovieService {
	List<Movie> findAllMovies();
	Movie findMovieById(Integer id);
    boolean createMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(Integer id);
    //boolean movieExists(String title, String category);
}
