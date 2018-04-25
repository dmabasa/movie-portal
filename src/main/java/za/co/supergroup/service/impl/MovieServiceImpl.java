package za.co.supergroup.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import za.co.supergroup.data.repository.jpa.MovieRepository;
import za.co.supergroup.model.jpa.Movie;
import za.co.supergroup.service.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService{

	@Resource
	private MovieRepository repository;
	
	@Override
	public List<Movie> findAllMovies() {
		return repository.findAll();
	}

	@Override
	public Movie findMovieById(Integer id) {
		
		 List<Movie> movies = repository.findAll();
		 for(Movie movie : movies)
		 {
			 if(movie.getId().intValue() == id.intValue())
			 {
				 return movie;
			 }
		 }
		return null;
	}

	@Override
	public boolean createMovie(Movie movie) {
		
		if(movieExists(movie.getCategory(), movie.getTitle()))
		{
			return false;
		}else
		{
			repository.save(movie);
			return true;
		}
		
	}

	@Override
	public void updateMovie(Movie movie) {
		repository.save(movie);
		
	}

	@Override
	public void deleteMovie(Integer id) {
		repository.deleteById(id);
		
	}

	private boolean movieExists(String category, String title) {
		List<Movie> movieList = repository.findByCategoryAndTitle(category, title);
		if(movieList.size() > 0)
		{
			return true;
		}
		return false;
	}

}
