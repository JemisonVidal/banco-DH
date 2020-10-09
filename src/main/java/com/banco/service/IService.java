package com.banco.service;

import java.util.List;

public interface IService<T> {
	public T create(T object);

	public T findById(Long id);

	public List<T> findAll();

	public T update(T object);

	public void delete(Long id);
}
