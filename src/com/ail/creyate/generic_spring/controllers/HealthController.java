package com.ail.creyate.generic_spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ail.creyate.generic_spring.db.dao.MenusDAO;
import com.ail.creyate.generic_spring.utils.Constants;

@Controller
@RequestMapping(value="/",method=RequestMethod.GET)
public class HealthController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HealthController.class);
	@Autowired(required=true)
	protected  MenusDAO menusDAO;
	
	@RequestMapping(value="health",method=RequestMethod.GET)
	public String checkHealth(HttpServletRequest request, HttpServletResponse response) {
		try{
			logger.info("checking health");
			menusDAO.findAll(null);
			request.setAttribute("message", "OK");
			return Constants.HEALTH_PAGE;
		}
		catch(Exception e){
			request.setAttribute("errorMsg", e.getMessage());
			return Constants.ERROR_VIEW;
		}
	}
	
	@RequestMapping(value="/showHomePage",method=RequestMethod.GET)
	public String showHome(HttpServletRequest request, HttpServletResponse response) {
		return Constants.MAIN_PAGE; 
	}
	
	
	
	
	
	
	
}
