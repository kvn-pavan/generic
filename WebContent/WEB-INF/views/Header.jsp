<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='shortcut icon' type='image/x-icon' href='/generic_spring/resources/images/favicon.ico' />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h2 style = "margin: 0px; padding-top: 10px; padding-bottom: 10px; background-color: white;text-align: center;"><a href='/generic_spring/home' style="text-decoration: none;"><img src="/generic_spring/resources/images/logo.png"> Bill of Material</a></h2>
<div style = "border: 0px solid #000000; float:left;" id="hierarchyDiv"></div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var pathName = $(location).attr('href');
		var cutPathName = pathName.substring(pathName.indexOf("generic_spring"));
		var parts = cutPathName.split("/");
		if(parts[1] == "home") {
			
		} else if(parts[1] == "garments") {
			var type = parts[2];
			var viewType = parts[3];
			
			if(viewType == "products" || viewType == "fabrics" || viewType == "trims") {
				var hierarchyStr = "<a href='/generic_spring/home'>Home</a> / <a href='/generic_spring/garments/"+type+"/lists/"+viewType+"/showList'>"+viewType+" list of "+type+"</a>";
				document.getElementById("hierarchyDiv").innerHTML = hierarchyStr;
			}
		}
	});
</script>

</html>