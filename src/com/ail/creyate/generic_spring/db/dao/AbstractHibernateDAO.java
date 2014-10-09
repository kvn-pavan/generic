package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractHibernateDAO< T extends Serializable > {
	 
	   private Class< T > clazz;
	 
	   private Logger logger = Logger.getLogger(AbstractHibernateDAO.class);
	   //@Autowired
	   protected SessionFactory sessionFactory;
	 
	   public void setClazz( Class clazzToSet ){
	      this.clazz = clazzToSet;
	   }
	 
	   public T findOne( T id, Transaction transaction ){
		  if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			  getCurrentSession().beginTransaction();
		  }
		  if(!getCurrentSession().getTransaction().isActive()) {
			  getCurrentSession().beginTransaction();
		  }
		  T result = (T) getCurrentSession().get( clazz, id );
	      if(transaction == null) {
	    	 // getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	      return result;
	   }
	   public List< T > findAll(Transaction transaction){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
	      List results = getCurrentSession().createQuery( "from " + clazz.getName() ).list();
	      if(transaction == null) {
	    	 // getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	      return results;
	   }
	 
	   public List<T> fetchData(String query,Transaction transaction){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
		   List results = getCurrentSession().createQuery(query).list();
		   if(transaction == null) {
			 //  getCurrentSession().getTransaction().commit();
			   commitOrRollback(getCurrentSession().getTransaction());
		   }
		   return results;
	   }
	   
	   public List<T> fetchDataByLimit(String query,int startIndex, int maxResults, Transaction transaction){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
			  }
		   Query result = getCurrentSession().createQuery(query);
		   result.setFirstResult(startIndex);
		   result.setMaxResults(maxResults);
		   List<T> finalResults = result.list();
		   if(transaction == null) {
			 //  getCurrentSession().getTransaction().commit();
			   commitOrRollback(getCurrentSession().getTransaction());
		   }
		   return finalResults;
	   }
	   
	   public void create( T entity, Transaction transaction ){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
	      getCurrentSession().persist( entity );
	      if(transaction == null) {
	    	 // getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	   }
	 
	   public void update( T entity, Transaction transaction ){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
	      getCurrentSession().merge( entity );
	      if(transaction == null) {
	    	//  getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	   }
	   
	   public void saveOrUpdate(T entity, Transaction transaction) {
		   if(transaction == null) {
			   if(!getCurrentSession().getTransaction().isActive()) {
				   getCurrentSession().beginTransaction();
			   }
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
		   getCurrentSession().saveOrUpdate(entity);
		   if(transaction == null) {
			  // getCurrentSession().getTransaction().commit();
			   commitOrRollback(getCurrentSession().getTransaction());
		   }
	   }
	   
	   public void commitOrRollback(Transaction transaction) {
		   if(transaction == null) {
			   return;
		   }
		   try {
			   transaction.commit();
		   } catch (Exception e) {
			   try {
				   transaction.rollback();
			   } catch (Exception e1) {
				   logger.error("Error in transaction rollback:"+e1.getMessage());
				   logger.error(e1);
			   }
			   logger.error("Error in transaction commit:"+e.getMessage());
			   logger.error(e);
			   throw e;
			   
		   }
	   }
	 
	   public void delete( T entity, Transaction transaction ){
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   getCurrentSession().beginTransaction();
		   }
		   if(!getCurrentSession().getTransaction().isActive()) {
				  getCurrentSession().beginTransaction();
		   }
	      getCurrentSession().delete( entity );
	      if(transaction == null) {
	    	 // getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	   }
	   public void deleteById( T entityId, Transaction transaction ){
		   Transaction trans = null;
		   if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   trans = getCurrentSession().beginTransaction();
		   }
	      T entity = findOne( entityId, trans );
	      delete( entity, trans );
	      if(transaction == null) {
	    	 // getCurrentSession().getTransaction().commit();
	    	  commitOrRollback(getCurrentSession().getTransaction());
	      }
	   }
	 
	   protected final Session getCurrentSession(){
		   if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			   sessionFactory.getCurrentSession().beginTransaction();
		   }
	      return sessionFactory.getCurrentSession();
	   }
	   
	   protected SessionFactory getCurrentSessionFactory() {
		   return this.sessionFactory;
	   }
	   

	   public Transaction getCurrentTransactionorCreateNew() {
		   Session currentSession = getCurrentSession();
		   Transaction transaction = currentSession.getTransaction();
		   if (transaction == null && !getCurrentSession().getTransaction().isActive()) {
			   transaction = currentSession.beginTransaction();
		   }

		   return transaction;
	   }
	   
	   protected abstract void setSessionFactory();
	   protected abstract void setSessionFactory(SessionFactory sessionFactory);
	}
