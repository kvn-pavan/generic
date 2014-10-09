package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ail.creyate.generic_spring.db.beans.SystemvarsBean;

@Repository
public class SystemvarsDAO extends GenericHibernateDAO<Serializable>{

	@Override
	protected void setSessionFactory() {
		if(this.sessionFactory == null) {
			this.sessionFactory = HibernateUtilities.getWebsiteDBSessionFactory();
		}
	}

	public SystemvarsDAO() {
		this.setSessionFactory();
		this.setClazz(SystemvarsBean.class);
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
