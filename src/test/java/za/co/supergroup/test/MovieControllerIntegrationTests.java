package za.co.supergroup.test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URI;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import za.co.supergroup.WebConfig;
import za.co.supergroup.model.jpa.Movie;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@Transactional
public class MovieControllerIntegrationTests {
	
	private static final String BASE_URI = "http://localhost:8080/api";
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    @Autowired
    private RestTemplate template;

    // =========================================== Get All Movies ==========================================

    @Test
    public void test_get_all_success(){
        ResponseEntity<Movie[]> response = template.getForEntity(BASE_URI + "/all-movies", Movie[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        
    }

    // =========================================== Get Movie By ID =========================================

    @Test
    public void test_get_by_id_success(){
        ResponseEntity<Movie> response = template.getForEntity(BASE_URI + "/movie/?id=1", Movie.class);
        Movie movie = response.getBody();
        assertThat(movie.getId(), is(1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        
    }

    @Test
    public void test_get_by_id_failure_not_found(){
        try {
            ResponseEntity<Movie> response = template.getForEntity(BASE_URI + "/movie/?id=10", Movie.class);
            //fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

   

    // =========================================== Update Existing Movie ===================================

    @Test
    public void test_update_movie_success(){
        //User existingUser = new User(2, "John Snow Updated");
    	Movie existingMovie = new Movie();
        existingMovie.setId(7);
        existingMovie.setTitle("Universal Soldier5");
        existingMovie.setCategory("Action 3");
        template.put(BASE_URI + "/movie", existingMovie);
    }

  

  
    // =========================================== Delete Movie ============================================

   @Test
    public void test_delete_movie_success(){
        template.delete(BASE_URI + "/movie/?id=" + getLastMovie().getId());
    }

    private Movie getLastMovie(){
        ResponseEntity<Movie[]> response = template.getForEntity(BASE_URI + "/all-movies", Movie[].class);
        Movie[] movies = response.getBody();
        return movies[movies.length - 1];
    }
    
    // =========================================== Create New Movie ========================================

    @Test
     public void test_create_new_movie_fail_exists(){
     	Movie movie = new Movie();
 	 	movie.setTitle("Universal Soldier2");
 	 	movie.setCategory("Action 1");
         try {
             URI location = template.postForLocation(BASE_URI + "/movie", movie, Movie.class);
             fail("should return 409 conflict");
         } catch (HttpClientErrorException e){
         	
             assertThat(e.getStatusCode(), is(HttpStatus.CONFLICT));
         }
     }
}
