package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.ail.creyate.generic_spring.db.beans.LogsBean;

@Repository
public class LogsDAO extends GenericHibernateDAO<Serializable>{

	@Override
	protected void setSessionFactory() {
		if(this.sessionFactory == null) {
			this.sessionFactory = HibernateUtilities.getWebsiteDBSessionFactory();
		}
	}

	public LogsDAO() {
		this.setSessionFactory();
		this.setClazz(LogsBean.class);
	}
	
	
	public LogsBean getLastUpdatedUserInfo(String pageId,String itemId,Transaction transaction){
		if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			  getCurrentSession().beginTransaction();
		}
		Query query = getCurrentSession().createQuery( "from  LogsBean where page_id = '"+pageId+"' and item_id='"+itemId+"'order by modified_time desc");
	    LogsBean logsBean = null;
	    if(query != null) {
		      List<LogsBean> result = query.list();
		      if(result != null && result.size() > 0) {
		    	  logsBean = result.get(0);
		      }
		}
		if(transaction == null) {
		    // getCurrentSession().getTransaction().commit();
			commitOrRollback(getCurrentSession().getTransaction());
		}
	    return logsBean;
	}
	
	
	@Override
	protected void setSessionFactory(SessionFactory sessionFactory) {
		if(sessionFactory == null){
			setSessionFactory();
		}
		else{
			this.sessionFactory = sessionFactory;
		}
	}
	
	public int getNumberOfResultsForId(String id, String key, Transaction transaction) {
		if(transaction == null && !getCurrentSession().getTransaction().isActive()) {
			getCurrentSession().beginTransaction();
		}
		Query query;
		if(key==null){
			query  =  getCurrentSession().createSQLQuery("select count(*) from logs where item_id='"+id+"'");	
		}
		else{
			query  =  getCurrentSession().createSQLQuery("select count(*) from logs where item_id='"+id+"'"+key);
		}
		int result = 0;
		if(query != null) {
			result = ((BigInteger)query.uniqueResult()).intValue();
		}
		if(transaction == null) {
			//getCurrentSession().getTransaction().commit();
			commitOrRollback(getCurrentSession().getTransaction());
		}
		return result;
	}

}
