package com.ail.creyate.generic_spring.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ail.creyate.generic_spring.db.beans.MenusBean;
import com.ail.creyate.generic_spring.db.beans.SystemvarsBean;
import com.ail.creyate.generic_spring.db.dao.MenusDAO;
import com.ail.creyate.generic_spring.db.dao.SystemvarsDAO;
import com.ail.creyate.generic_spring.utils.Resources;

@Service
public class ResourcesModel extends BasicModel {

	@Autowired(required=true)
	private MenusDAO menusDAO;
	
	@Autowired(required=true)
	private SystemvarsDAO systemvarsDAO;
	
	public void fetchAllMenus() {
		if(menusDAO == null) {
			menusDAO = new MenusDAO();
		}
		List<Serializable> menusSer = menusDAO.findAll(null);
		Resources.menuMap = new HashMap<String, Map<String,List<String>>>();
		for(int i = 0; i < menusSer.size(); i++) {
			MenusBean menu = (MenusBean)menusSer.get(i);
			if(menu.getCategory() != null) {
				Map<String, List<String>> categoryMap = Resources.menuMap.get(menu.getCategory());
				if(categoryMap == null) {
					categoryMap = new HashMap<String, List<String>>();
					List<String> values = new ArrayList<String>();
					values.add(menu.getValue());
					categoryMap.put(menu.getItemName(), values);
					Resources.menuMap.put(menu.getCategory(), categoryMap);
					
				} else {
					List<String> values = categoryMap.get(menu.getItemName());
					if(values == null) {
						values = new ArrayList<String>();
					}
					values.add(menu.getValue());
					categoryMap.put(menu.getItemName(), values);
					Resources.menuMap.put(menu.getCategory(), categoryMap);
					
				}
			}
		}
	}
	
	public void addMenuItem(MenusBean menusBean) {
		menusDAO.create(menusBean, null);
	}
	
	public void saveSystemVars(SystemvarsBean systemvarsBean) {
		systemvarsDAO.saveOrUpdate(systemvarsBean, null);
	}
	
	public void fetchAllSystemvars() {
		if(systemvarsDAO == null) {
			systemvarsDAO = new SystemvarsDAO();
		}
		List<Serializable> systemvarsSer = systemvarsDAO.findAll(null);
		Resources.systemVars = new HashMap<String, String>();
		for(int i = 0; i < systemvarsSer.size(); i++) {
			SystemvarsBean systemvar = (SystemvarsBean)systemvarsSer.get(i);
			Resources.systemVars.put(systemvar.getKey(), systemvar.getValue());
		}
	}
}
