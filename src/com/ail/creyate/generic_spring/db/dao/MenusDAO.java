package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ail.creyate.generic_spring.db.beans.MenusBean;

@Repository
public class MenusDAO extends GenericHibernateDAO<Serializable>{

	@Override
	protected void setSessionFactory() {
		if(this.sessionFactory == null) {
			this.sessionFactory = HibernateUtilities.getWebsiteDBSessionFactory();
		}
	}

	public MenusDAO() {
		this.setSessionFactory();
		this.setClazz(MenusBean.class);
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
