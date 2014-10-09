package com.ail.creyate.generic_spring.db.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.ail.creyate.generic_spring.models.ResourcesModel;
import com.ail.creyate.generic_spring.utils.ApplicationProperties;
 
public class HibernateUtilities {
	//For website db
	private static SessionFactory sessionFactoryDB1;
	private static ServiceRegistry serviceRegistryDB1;
	
	//For Orders DB
	private static SessionFactory sessionFactoryDB2;
	private static ServiceRegistry serviceRegistryDB2;
	
	//For ERP DB
	private static SessionFactory sessionFactoryDB3;
	private static ServiceRegistry serviceRegistryDB3;
	
	//For CMS DB
	private static SessionFactory sessionFactoryDB4;
	private static ServiceRegistry serviceRegistryDB4;

    static {
        try {
            Configuration configurationdb1 = new Configuration().configure("hibernate-website.cfg.xml");
            serviceRegistryDB1 = new StandardServiceRegistryBuilder().applySettings(
                    configurationdb1.getProperties()).build();
            sessionFactoryDB1 = configurationdb1.buildSessionFactory(serviceRegistryDB1);
        } catch (HibernateException exception) {
            System.out.println("Problem creating Website session factory "+exception);
        }
        
        try {
        	sessionFactoryDB2 = getCustomDBSessionFactory("mysql", "hibernate-orders.cfg.xml", "orderdb", ApplicationProperties.getProperty("environment"));
            /*Configuration configurationdb2 = new Configuration().configure("hibernate-orders.cfg.xml");
 
            serviceRegistryDB2 = new StandardServiceRegistryBuilder().applySettings(
                    configurationdb2.getProperties()).build();
            sessionFactoryDB2 = configurationdb2.buildSessionFactory(serviceRegistryDB2);*/
        } catch (HibernateException exception) {
            System.out.println("Problem creating Orders db session factory "+exception);
        }
        
        try {
        	sessionFactoryDB3 = getCustomDBSessionFactory("oracle", "hibernate-erp.cfg.xml", "erpdb", ApplicationProperties.getProperty("environment"));
            /*Configuration configurationdb3 = new Configuration().configure("hibernate-erp.cfg.xml");
 
            serviceRegistryDB3 = new StandardServiceRegistryBuilder().applySettings(
                    configurationdb3.getProperties()).build();
            sessionFactoryDB3 = configurationdb3.buildSessionFactory(serviceRegistryDB3);*/
        } catch (HibernateException exception) {
            System.out.println("Problem creating ERP db session factory "+exception);
        }
        try {
        	sessionFactoryDB4 = getCustomDBSessionFactory("mysql", "hibernate-cms.cfg.xml", "cmsdb", ApplicationProperties.getProperty("environment"));
            /*Configuration configurationdb4 = new Configuration().configure("hibernate-cms.cfg.xml");
 
            serviceRegistryDB4 = new StandardServiceRegistryBuilder().applySettings(
                    configurationdb4.getProperties()).build();
            sessionFactoryDB4 = configurationdb4.buildSessionFactory(serviceRegistryDB4);*/
        } catch (HibernateException exception) {
            System.out.println("Problem creating CMS db session factory "+exception);
        }
        
        //Load some resources after server start
        ResourcesModel resourcesModel = new ResourcesModel();
        resourcesModel.fetchAllMenus();
        resourcesModel.fetchAllSystemvars();
    }
 
    public static SessionFactory getWebsiteDBSessionFactory() {
        return sessionFactoryDB1;
    }
    
    public static SessionFactory getOrderDBSessionFactory() {
        return sessionFactoryDB2;
    }
    
    public static SessionFactory getERPDBSessionFactory() {
        return sessionFactoryDB3;
    }
    
    public static SessionFactory getCMSDBSessionFactory() {
        return sessionFactoryDB4; 
    }
   
    
    private static SessionFactory getSessionFactory(String dbType, String dbPrefix, String env, String configFile) {
    	SessionFactory  sessionFactory;
    	ServiceRegistry serviceRegistry;
    	try {
             Configuration configurationdb = getConfiguration(dbType, dbPrefix, env, configFile);
             serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                     configurationdb.getProperties()).build();
             sessionFactory = configurationdb.buildSessionFactory(serviceRegistry);
         } catch (HibernateException exception) {
             System.out.println("Problem creating Custom DB session factory "+exception);
             return null;
         }
    	return sessionFactory;
    }
    
    private static Configuration getConfiguration(String dbType, String dbPrefix, String env, String configFile){
    	try{
    		String db = ApplicationProperties.getProperty(dbPrefix+"."+env+".db");
    		String url = ApplicationProperties.getProperty(dbPrefix+"."+env+".host")+"/"+db+"?zeroDateTimeBehavior=convertToNull";
    		if("oracle".equals(dbType)) {
    			url = "jdbc:oracle:thin:@"+ApplicationProperties.getProperty(dbPrefix+"."+env+".host")+":"+db;
    		}
    		String username = ApplicationProperties.getProperty(dbPrefix+"."+env+".username");
    		String password = ApplicationProperties.getProperty(dbPrefix+"."+env+".password");
    		Configuration configurationdb = new Configuration().configure(configFile);
    		configurationdb.setProperty("connection.url", url);
    		configurationdb.setProperty("connection.username", username);
    		configurationdb.setProperty("connection.password", password);
    		configurationdb.setProperty("hibernate.connection.url", url);
    		configurationdb.setProperty("hibernate.connection.username", username);
    		configurationdb.setProperty("hibernate.connection.password", password);
    		configurationdb.setProperty("hibernate.c3p0.min_size", "3");
    		configurationdb.setProperty("hibernate.c3p0.max_size", "5");
    		
    		if(dbPrefix.equalsIgnoreCase("cmsdb")) {
    			//configurationdb.setProperty("hbm2ddl.auto", "none");
    		}
    		
    		return configurationdb;
    	}
    	catch(Exception e){
    		return null;
    	}
    }
    
    public static SessionFactory getCustomDBSessionFactory(String environment, String database){
    	String configFile = "hibernate-website.cfg.xml";
    	return getSessionFactory("mysql", "database", environment, configFile);
    }
    
    public static SessionFactory getCustomDBSessionFactory(String dbType, String configFile, String dbPrefix, String environment) {
    	return getSessionFactory(dbType, dbPrefix, environment, configFile);
    }
 
}
