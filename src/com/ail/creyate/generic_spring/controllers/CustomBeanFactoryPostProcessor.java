package com.ail.creyate.generic_spring.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	/**
	 * This method sets the bean scope to request for all the beans declared
	 * */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)	throws BeansException {
		for (String beanName : factory.getBeanNamesForAnnotation(Component.class)) {
			BeanDefinition beanDef = factory.getBeanDefinition(beanName);
			String explicitScope = beanDef.getScope();
			if (StringUtils.trimToNull(explicitScope) == null || (StringUtils.trimToNull(explicitScope) != null && explicitScope.equalsIgnoreCase("singleton"))) {
				beanDef.setScope("request");
			}
		}
	}

}
