package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
	 
	   T findOne(final long id);
	 
	   List<T> findAll();
	 
	   void create(final T entity);
	 
	   void update(final T entity);
	 
	   void delete(final T entity);
	 
	   void deleteById(final long entityId);
}
