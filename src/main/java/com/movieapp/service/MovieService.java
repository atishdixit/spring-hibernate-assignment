package com.movieapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movieapp.convertor.ConverterUtility;
import com.movieapp.entity.MovieEntity;
import com.movieapp.model.MovieTO;
import com.movieapp.repository.MovieRepository;

@Service
@Transactional
public class MovieService implements BaseService<MovieTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	private String fieldStatusMsg = null;;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<MovieTO> getAll() {
		LOGGER.info("Inside get All method ::");
		List<MovieTO> list = new ArrayList<>();
		movieRepository.findAll().forEach(app -> {
			list.add(ConverterUtility.convertMovieEntityToMovieTO(app));
		});
		return list;
	}

	@Override
	public MovieTO getMovie(Integer id) {
		LOGGER.info("Inside get movie method ::");
		return ConverterUtility.convertMovieEntityToMovieTO(movieRepository.getOne(id));
	}

	@Transactional
	@Override
	public MovieTO save(MovieTO movie) {
		LOGGER.info("Inside save Movie method ::");

		if (!verfyFields(movie)) {
			throw new IllegalArgumentException(fieldStatusMsg);
		}

		MovieEntity appDetailEntity = movieRepository.save(ConverterUtility.convertMovieToToMovieEntity(movie));
		return ConverterUtility.convertMovieEntityToMovieTO(appDetailEntity);
	}

	@Transactional
	@Override
	public boolean delete(MovieTO app) {
		LOGGER.info("Inside delete method ::");
		MovieEntity movieEntity = ConverterUtility.convertMovieToToMovieEntity(app);
		movieEntity.setId(app.getId());
		try {
		  movieRepository.delete(movieEntity);
		  return true;
		}catch (Exception e) {
			return false;
		}
		
	}

	@PostConstruct
	public void addData() {
		LOGGER.info("Inside add date method ::");
		List<MovieTO> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			list.add(new MovieTO(10 + i, "Title" + i, "Categorie" + i, 4));
		}

		this.saveAll(list);
	}

	@Override
	public void saveAll(List<MovieTO> moviesList) {
		LOGGER.info("Inside get All movies method ::");
		movieRepository.saveAll(ConverterUtility.toMovieEntity(moviesList));
	}

	@Transactional
	@Override
	public MovieTO update(MovieTO movieTo) {
		LOGGER.info("Inside update method ::");

		if (!verfyFields(movieTo)) {
			throw new IllegalArgumentException(fieldStatusMsg);
		}

		MovieEntity movie = movieRepository.getOne(movieTo.getId());
		if (movie != null) {
			movie.setCategory(movieTo.getCategory() == null ? movie.getCategory() : movieTo.getCategory());
			movie.setTitle(movieTo.getTitle() == null ? movie.getTitle() : movieTo.getTitle());
			movie.setRating(movieTo.getRating() == 0.0 ? movie.getRating() : movieTo.getRating());
			movieRepository.save(movie);
		} else {
			return null;
		}

		return movieTo;
	}

	/**
	 * Method to verify the rating
	 * 
	 * @param rating
	 * @return
	 */
	private boolean verifyRating(double rating) {
		return ((rating >= 0.5) && (rating <= 5.0));
	}

	private boolean verfyFields(MovieTO movie) {
		boolean fieldStatus = true;
		if (!verifyRating(movie.getRating())) {
			fieldStatusMsg = "Rating should be in Range [0.5 to 5]";
			fieldStatus = false;
		} else if (movie.getCategory() == null || movie.getCategory().length() < 0) {
			fieldStatusMsg = "Category can not empty ";
			fieldStatus = false;
		} else if (movie.getTitle() == null || movie.getTitle().length() < 0) {
			fieldStatusMsg = "Title can not empty ";
			fieldStatus = false;
		}

		return fieldStatus;

	}
}
