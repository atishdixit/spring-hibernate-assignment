package com.movieapp.service;

import java.util.List;

public interface BaseService<T> {
	public List<T> getAll();

	public T getMovie(Integer id);

	public T save(T movie);

	public boolean delete(T movie);

	public void saveAll(List<T> movie);
	
	public T update(T movie);
	
}
