package com.ail.creyate.generic_spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ail.creyate.generic_spring.db.beans.MenusBean;
import com.ail.creyate.generic_spring.db.beans.SystemvarsBean;
import com.ail.creyate.generic_spring.models.ResourcesModel;
import com.ail.creyate.generic_spring.utils.Constants;
import com.ail.creyate.generic_spring.utils.GenericUtils;

@Controller
@RequestMapping(value="/resources")
public class ResourcesController extends BaseController {
	
	@Autowired(required=true)
	ResourcesModel resourcesModel;
	
	@RequestMapping(value="/loadAll")
	public void loadResources() {
		resourcesModel.fetchAllMenus();
		resourcesModel.fetchAllSystemvars();
		//Can add other methods here which are needed to be reloaded from database on demand and should be run on server start
	}
	
	@RequestMapping(value="/menus/showAll") 
	public String showAllMenus(HttpServletRequest request, HttpServletResponse response) {
		return Constants.MENUS_ADD_PAGE;
	}
	
	@RequestMapping(value="/menus/addNewItem", method = RequestMethod.POST)
	public String addMenuItem(HttpServletRequest request, HttpServletResponse response) {
		String category = GenericUtils.getRequestParameter(request, "category", null);
		String itemName = GenericUtils.getRequestParameter(request, "itemName", null);
		String value = GenericUtils.getRequestParameter(request, "value", null);
		
		if(category != null && itemName != null && value != null) {
			MenusBean menusBean = new MenusBean();
			menusBean.setCategory(category);
			menusBean.setItemName(itemName);
			menusBean.setValue(value);
			
			resourcesModel.addMenuItem(menusBean);
			resourcesModel.fetchAllMenus();
		}
		return Constants.MENUS_ADD_PAGE;
	}
	
	@RequestMapping(value="/system/addVars", method = {RequestMethod.GET, RequestMethod.POST})
	public String addSystemVars(HttpServletRequest request, HttpServletResponse response) {
		String key = GenericUtils.getRequestParameter(request, "key", null);
		String value = GenericUtils.getRequestParameter(request, "value", null);
		
		if(key != null && value != null) {
			SystemvarsBean systemvarsBean = new SystemvarsBean();
			systemvarsBean.setKey(key);
			systemvarsBean.setValue(value);
			
			resourcesModel.saveSystemVars(systemvarsBean);
			resourcesModel.fetchAllSystemvars();
		}
		return Constants.SYSTEMVARS_ADD_PAGE;
	}
}
