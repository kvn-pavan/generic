package com.ail.creyate.generic_spring.utils;

import java.lang.reflect.Field;

import javax.persistence.Column;

public class DatabaseUtils {

	public static String[] getColumns(Class beanClass){
	//	Class beanClass= bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();
		String[] columns = new String[fields.length];
		int i=0;
		for(Field field : fields){
			 Class type = field.getType();
			 String name = field.getName();
			 String column_name = field.getAnnotation(Column.class).name();
			 columns[i++] = column_name;
		}
		return columns;
	}

	public static String getSQLWhereClauseQuery(String tableName,String column, String values){
		String sqlQuery = "FROM "+tableName+" where "+column+" in ("+values+")";
		return sqlQuery;
	}
	
	public static String getSQLForAll(String tableName){
		String sqlQuery = "FROM "+tableName;
		return sqlQuery;
	}
}
