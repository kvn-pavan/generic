package com.ail.creyate.generic_spring.db.dao;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public abstract class GenericHibernateDAO< T extends Serializable > extends AbstractHibernateDAO< T > {
	
}