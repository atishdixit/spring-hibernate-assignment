package com.movieapp;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.movieapp.model.MovieTO;
import com.movieapp.service.MovieService;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieApplicationTests {

	public MovieApplicationTests() {
	}

	@Autowired
	private MovieService movieservice;

	@Test
	void createNewMovie() {
		MovieTO movie = new MovieTO(100, "movie1", "Cate1", 4.5);
		MovieTO movieResponse = movieservice.save(movie);
		Assert.assertEquals(movieResponse.getCategory(), movie.getCategory());
	}

	@Test
	void getAllMovies() {
		MovieTO movie1 = new MovieTO(100, "movie1", "Cate1", 4.5);
		MovieTO movie2 = new MovieTO(100, "movie1", "Cate1", 4.5);

		MovieTO movieResponse1 = movieservice.save(movie1);
		MovieTO movieResponse2 = movieservice.save(movie2);
		List<MovieTO> movieList = movieservice.getAll();

		Assert.assertTrue(movieList.contains(movieResponse1));
		Assert.assertTrue(movieList.contains(movieResponse2));
	}

	@Test
	void deleteMovie() {
		MovieTO movie = new MovieTO(1, "movie", "Category_2", 4.5);
		MovieTO movieResponse = movieservice.save(movie);
		Assert.assertTrue(movieResponse != null);

		movieservice.delete(movie);
		movie = movieservice.getMovie(movieResponse.getId());
		Assert.assertTrue(movie.getCategory() != "Category_2");
	}

	@Test
	void updateMovie() {
		MovieTO movie = new MovieTO(1, "movie_8", "Category_3", 4.5);
		MovieTO movieResponse = movieservice.save(movie);

		Assert.assertTrue(movieResponse != null);

		movie = movieservice.getMovie(movieResponse.getId());
		Assert.assertTrue(movie != null);

		movie.setCategory("New Category");
		movieResponse = movieservice.update(movie);
		movie = movieservice.getMovie(movieResponse.getId());
		Assert.assertTrue(movie.getCategory().equals("New Category"));
	}

	@Test
	void movie_not_found_for_incorrect_Id() {
		try {
			movieservice.getMovie(2001);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true); 
		}
	}
	
	@Test
	void thow_Exception_if_rating_Not_between_0_to_5() {
		try {
			MovieTO movie = new MovieTO(1, "movie_8", "Category_3", 8);
			movieservice.save(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	

	@Test
	void thow_Exception_if_rating_LestThan_Half() {
		try {
			MovieTO movie = new MovieTO(1, "movie_8", "Category_3", 0.4);
			movieservice.save(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	
	
	@Test
	void thowExceptionOnAddNewMovieIfCategoryIsEmpty() {
		try {
			MovieTO movie = new MovieTO(1, "movie_8", null, 8);
			movieservice.save(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	

	@Test
	void thowExceptionOnUpdateMovieIfCategoryIsEmpty() {
		try {
			MovieTO movie = new MovieTO(1, "movie_8", null, 4);
			movieservice.update(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	void thowExceptionOnAddNewMovieIfTitleIsEmpty() {
		try {
			MovieTO movie = new MovieTO(1, null, "Category", 4);
			movieservice.save(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	

	@Test
	void thowExceptionOnUpdateMovieIfTitleIsEmpty() {
		try {
			MovieTO movie = new MovieTO(1, null, "Category", 8);
			movieservice.update(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	void thowExceptionOnUpdateMovieWithNoID() {
		try {
			MovieTO movie = new MovieTO(null, "movie", "Category", 5);
			movieservice.update(movie);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	void CreateNewMovieWithNoId() {
		try {
			MovieTO movie = new MovieTO(null, "Title", "Category", 5);
			movieservice.save(movie);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
}
