<%@page import="com.ail.creyate.generic_spring.utils.Resources"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add System Resources here</title>
<link href="/generic_spring/resources/css/style.css" rel="Stylesheet" type="text/css" />
</head>
<% 
	Map<String, String> systemVars = Resources.systemVars;
%>
<body bgcolor="#c2dfff">
<%@include file="../../Header.jsp" %>
	<div id="addSystemVarDiv">
	<b>Add New System Variable here: </b>
	<form method="post" action="/generic_spring/resources/system/addVars">
		<table>
			<tr>
				<td>
					Key
				</td>
				<td> 
					<input type="text" name="key" /> 
				</td>
			</tr>
			<tr>
				<td>
					Value
				</td>
				<td> 
					<textarea rows="3" cols="100" name="value"></textarea> 
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="button" type="submit" value="Add" />
				</td>
				
			</tr>
		</table>
	</form>
	</div>
	<div id="showMenuItemsDiv">
		<table>
			<tr>
				<th>
					Key
				</th>
				<th>
					Value
				</th>
			</tr>
			<% 
				for (Map.Entry<String, String> entry : systemVars.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
			%>
				<tr>
					<td>
						<%=key%>
					</td>
					<td>
						<form method="post" action="/generic_spring/resources/system/addVars">
							<textarea rows="3" cols="100" name="value"><%=value%></textarea>
							<input type= "hidden" name="key" value=<%=key %> />
							<input class="button" type = "submit" value="Change" />
						</form>
					</td>
				</tr>
			<% 			
				}
			%>
		</table>
	</div>
</body>
</html>