package com.ail.creyate.generic_spring.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resources {
	
	//This has all the menus. The first level keys are category like Mens-FORMAL-SHIRT. The second level keys are items like color or pattern etc
	public static Map<String, Map<String, List<String>>> menuMap = new HashMap<String, Map<String, List<String>>>();
	
	//This has all the system level variables
	public static Map<String, String> systemVars = new HashMap<String, String>();
	
	public static List<String> getMenuItems(String category, String itemName) {
		if(menuMap != null) {
			Map<String, List<String>> categoryMenu = menuMap.get(category);
			Map<String, List<String>> genericMenu = null;
			if(!category.equalsIgnoreCase("all")) {
				genericMenu = menuMap.get("all");
			}
			if(categoryMenu == null && genericMenu != null) {
				return genericMenu.get(itemName);
			} else if(categoryMenu != null && genericMenu == null) {
				return categoryMenu.get(itemName);
			} else if(categoryMenu != null && genericMenu != null){
				List<String> result = new ArrayList<String>();
				if(categoryMenu.containsKey(itemName)){
					result.addAll(categoryMenu.get(itemName));
				}
				if(genericMenu.containsKey(itemName)){
					result.addAll(genericMenu.get(itemName));
				}
				return result;
			}
			
		}
		return null;
	}
	
	public static String getSystemVariable(String key) {
		if(systemVars != null) {
			return systemVars.get(key);
		}
		return null;
	}
	
	public static Boolean setSystemVariable(String key, String value) {
		if(systemVars != null) {
			systemVars.put(key, value);
			return true;
		}
		return false;
	}
	
	public static List<String> getSystemVariableList(String key) {
		List<String> result = new ArrayList<String>();
		if(systemVars != null) {
			String value = systemVars.get(key);
			if(value ==  null) {
				return null;
			}
			String[] valuesArr = value.split(",");
			for(int i = 0; i < valuesArr.length; i++) {
				result.add(valuesArr[i].trim());
			}
			return result;
		}
		return null;
	}
	
}
