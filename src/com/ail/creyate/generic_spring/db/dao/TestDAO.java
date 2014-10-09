package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ail.creyate.generic_spring.db.beans.TestBean;

@Repository
public class TestDAO extends GenericHibernateDAO<Serializable> {
	
	@Override
	protected void setSessionFactory() {
		if(this.sessionFactory == null) {
			this.sessionFactory = HibernateUtilities.getWebsiteDBSessionFactory();
		}
	}

	public TestDAO() {
		this.setSessionFactory();
		this.setClazz(TestBean.class);
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		if(sessionFactory == null){
			setSessionFactory();
		}
		else{
			this.sessionFactory = sessionFactory;
		}		
	}
	
	public void setSessionFactory(boolean force) {
		if(force) {
			this.sessionFactory = HibernateUtilities.getWebsiteDBSessionFactory();
		}
	}
	
}
