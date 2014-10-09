<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.ail.creyate.generic_spring.utils.Resources"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add New Menu Item</title>
<link href="/generic_spring/resources/css/style.css" rel="Stylesheet" type="text/css" />
</head>
<%
Map<String, Map<String, List<String>>> menus = Resources.menuMap;

%>
<body bgcolor="#c2dfff" >
<%@include file="../../Header.jsp" %>
	<div id="addMenuItemDiv">
	<b>Add New Menu Item here: </b>
	<form method="post" action="/generic_spring/resources/menus/addNewItem">
		<table>
			<tr>
				<td>
					Category
				</td>
				<td> 
					<input type="text" name="category" value="Mens-FORMAL-SHIRT"/> 
				</td>
			</tr>
			<tr>
				<td>
					Menu Item Name
				</td>
				<td> 
					<input type="text" name="itemName" /> 
				</td>
			</tr>
			<tr>
				<td>
					Menu Item Value
				</td>
				<td> 
					<input type="text" name="value" /> 
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="Add" />
				</td>
				
			</tr>
		</table>
	</form>
	</div>
	<div id="showMenuItemsDiv">
		<table>
			<tr>
				<th>
					Category
				</th>
				<th>
					Menu Item Name
				</th>
				<th>
					Menu Item Value
				</th>
			</tr>
			<% 
				for (Map.Entry<String, Map<String, List<String>>> entry : Resources.menuMap.entrySet()) {
				    String category = entry.getKey();
				    Map<String, List<String>> categoryMap = entry.getValue();
				    for(Map.Entry<String, List<String>> entry2 : categoryMap.entrySet()) {
				    	String itemName = entry2.getKey();
				    	List<String> values = entry2.getValue();
				    	for(int i = 0; i < values.size(); i++) {
				    		String value = values.get(i);
				    	
			%>
				<tr>
					<td>
						<%=category%>
					</td>
					<td>
						<%=itemName%>
					</td>
					<td>
						<%=value%>
					</td>
				</tr>
			<% 			}
				    }
				}
			%>
		</table>
	</div>
</body>
</html>