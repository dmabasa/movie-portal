package za.co.supergroup.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.supergroup.model.jpa.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	public List<Movie> findByCategoryAndTitle (String category, String title);
}
