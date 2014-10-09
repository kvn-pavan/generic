package com.ail.creyate.generic_spring.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.amazonaws.util.json.JSONObject;

@Component
public class LoggingUtil {
	
	
	public static<T> String getChangedFields(T oldBean, T newBean){
		try{
			JSONObject result = new JSONObject();
			if(oldBean.getClass() != newBean.getClass()){
				return null;
			}
			
			Class temp = oldBean.getClass();
			Field[] fields = temp.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				Object oldObj = fields[i].get(oldBean);
				Object newObj = fields[i].get(newBean);
				String oldVal;
				String newVal;
				if(oldObj == null){
					oldVal = "";
				}
				else{
					oldVal = oldObj.toString();
				}
				if(newObj == null){
					newVal = "";
				}
				else{
					newVal = newObj.toString();
				}
				
				if(!oldVal.equals(newVal)){
					JSONObject values = new JSONObject();
					values.put("old_value", oldVal);
					values.put("new_value",newVal);
					result.put(fields[i].getName(), values);
				}
			}
			return result.toString();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static<T> String getId(T bean){
		try{
			JSONObject result = new JSONObject();
			
			Class beanClass = bean.getClass();
			Method method = beanClass.getDeclaredMethod("getId");
			Object obj = method.invoke(bean);
			if(obj == null){
				return null;
			}
			else{
				return (String)obj;
			}
		}
		catch(Exception e){
			return null;
		}
	}
	
	
	
}
