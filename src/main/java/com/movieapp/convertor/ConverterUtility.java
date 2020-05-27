package com.movieapp.convertor;

import java.util.ArrayList;
import java.util.List;

import com.movieapp.entity.MovieEntity;
import com.movieapp.model.MovieTO;

/**
 * This Utility that convert Entity to dto vis-versa
 * 
 * @author Admin
 *
 */
public class ConverterUtility {

	/**
	 * No need to create Object of class out side 
	 */
	private ConverterUtility() {
	}
	
	/**
	 * Method to convert MovieEntity To MovieTO
	 * 
	 * @param movieEntity
	 * @return
	 */
	public static MovieTO convertMovieEntityToMovieTO(MovieEntity movieEntity) {
		MovieTO movieTO = new MovieTO();
		movieTO.setId(movieEntity.getId());
		movieTO.setTitle(movieEntity.getTitle());
		movieTO.setRating(movieEntity.getRating());
		movieTO.setCategory(movieEntity.getCategory());
	
		return movieTO;
	}

	/**
	 * Method to convert MovieTo To MovieEntity
	 * 
	 * @param movieTO
	 * @return
	 */
	public static MovieEntity convertMovieToToMovieEntity(MovieTO movieTO) {
		MovieEntity appDetailEntity = new MovieEntity();
		appDetailEntity.setRating(movieTO.getRating());
		appDetailEntity.setId(movieTO.getId());
		appDetailEntity.setCategory(movieTO.getCategory());
		appDetailEntity.setTitle(movieTO.getTitle());
		return appDetailEntity;
	}

	/**
	 * Method to connvert MovieTo list to MovieEntity list
	 * @param movies
	 * @return
	 */
	public static List<MovieEntity> toMovieEntity(List<MovieTO> movies) {
		List<MovieEntity> list = new ArrayList<>();
		movies.forEach(movie -> list.add(convertMovieToToMovieEntity(movie)));
		return list;
	}

}
