package com.ail.creyate.generic_spring.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestData {
	
	public static String getUserName(HttpServletRequest request){
		if(request.getSession() != null){
			Object nameObj = request.getSession().getAttribute("name");
			if(nameObj != null && nameObj instanceof String){
				return (String)nameObj;
			}
		}
		
		return "anonymous";
		
	}
	
	public static String getUserEmail(HttpServletRequest request){
		if(request.getSession() != null){
			Object emailObj = request.getSession().getAttribute("email");
			if(emailObj != null && emailObj instanceof String){
				return (String)emailObj;
			}
		}
		return "anonymous";
	}
	
	public static boolean isAdmin(HttpServletRequest request){
		if(request.getSession() != null){
			Object isAdmin = request.getSession().getAttribute("admin_prod");
			if(isAdmin != null && isAdmin instanceof Boolean){
				return (Boolean)isAdmin;
			}
		}
		return false;
	}

}
