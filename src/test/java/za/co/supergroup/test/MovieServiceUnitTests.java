package za.co.supergroup.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import za.co.supergroup.model.jpa.Movie;
import za.co.supergroup.service.IMovieService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MovieServiceUnitTests {

	@Autowired
	private IMovieService movieService;
	
	
	@Test 
	public void findAllMovies()
	{
		List<Movie> movies = movieService.findAllMovies();
		assertEquals(5, movies.size());
	}
	
	
	@Test
    public void findMovieById() {
		Movie movie = movieService.findMovieById(1);
		assertNotNull(movie);
    }
	
	
	@Test 
	public void createMovie()
	{
		Movie movie = new Movie();
		movie.setCategory("Category1");
		movie .setTitle("Title1");
		assert(movieService.createMovie(movie));
	}
	
	@Test 
	public void updateMovie()
	{
		Movie movie = movieService.findMovieById(1);
		movie.setTitle("Updated Title");
		movieService.updateMovie(movie);
		movie = movieService.findMovieById(1);
		assertEquals(movie.getTitle(), "Updated Title");
	}
	
	@Test 
	public void deleteMovie()
	{
		movieService.deleteMovie(1);
		Movie movie = movieService.findMovieById(1);
		assertNull(movie);
	}
	
	
}
