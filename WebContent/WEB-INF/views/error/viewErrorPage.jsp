<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>generic_spring Error</title>
</head>
<%
	Object errorObj = request.getAttribute("errorMsg");
	Object errorCauseObj = request.getAttribute("errorCause");
	String errorMsg = "";
	Exception errorCause = null;
	if(errorObj != null) {
		 errorMsg = (String)errorObj;
	}
	if(errorCauseObj != null) {
		errorCause = (Exception)errorCauseObj;
	}
%>
<body>
	<b>Something is not right here..Contact Tech Team</b>
	<br/>
	
	<div id="errorMsgDiv"><%=errorMsg %></div>	
	<% if(errorCause != null){
		StackTraceElement[] array = errorCause.getStackTrace();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i < array.length; i++){
			sb.append(array[i].getClassName() + " " + array[i].getFileName() + " " + array[i].getLineNumber() + " " + array[i].getMethodName()).append("\n<br>");
		}
		%>
	}
	<div id="cause" style="display: none;"><%= sb.toString() %></div>
	
	<%} %>
	
</body>
</html>