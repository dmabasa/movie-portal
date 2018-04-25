package za.co.supergroup.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import za.co.supergroup.model.jpa.Movie;
import za.co.supergroup.service.IMovieService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("api")
public class MovieController {
	
	 private final Logger LOG = LoggerFactory.getLogger(MovieController.class);
	
	
	@Autowired
	private IMovieService movieService;
	@GetMapping("movie")
	public ResponseEntity<Movie> getMovieById(@RequestParam("id") String id) {
		Movie movie = movieService.findMovieById(Integer.parseInt(id));
		
		if (movie == null){
            LOG.info("Movie with id {} not found", id);
            return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	@GetMapping("all-movies")
	public ResponseEntity<List<Movie>> findAllMovies() {
		List<Movie> list = movieService.findAllMovies();
		return new ResponseEntity<List<Movie>>(list, HttpStatus.OK);
	}
	@PostMapping("movie")
	public ResponseEntity<Void> createMovie(@RequestBody Movie movie, UriComponentsBuilder builder) {
        boolean flag = movieService.createMovie(movie);
        if (flag == false) {
        	LOG.info("************************************************************* Movie already exists *****************");
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        LOG.info("*************************************************************Createing a movie *****************");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/movie?id={id}").buildAndExpand(movie.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("movie")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
		movieService.updateMovie(movie);
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	@DeleteMapping("movie")
	public ResponseEntity<Void> deleteMovie(@RequestParam("id") String id) {
		movieService.deleteMovie(Integer.parseInt(id));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
}
