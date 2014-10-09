/**
 * This file has generic methods for Basic JSON editing
 */

var parseSimpleJson =  function(formId, fieldId, divId, json, isKeyEditable, isDeleteNeeded) {
	json = $.parseJSON(json);
	if(json instanceof Array) {
		var fullHtml = "";
		var addNewHtml = "";
		for(var i = 0; i < json.length; i++) {
			var html = "";
			var jsonObj = json[i];
			html = "<div id='"+divId+"_"+i+"'>";
			for (var key in jsonObj) {
				if (jsonObj.hasOwnProperty(key)) {
				    var val = jsonObj[key];
				    if(isKeyEditable) {
				    	html = html + "<input type='text' class='"+key+"ZXYXZ"+val+"' value='"+key+"' /><input type='text' id='"+key+"ZXYXZ"+val+"' value='"+val+"' />";
				    } else {
				    	html = html + "<label class='"+key+"ZXYXZ"+val+"'>"+key+"</label>"+"<input type='text' id='"+key+"ZXYXZ"+val+"' value='"+val+"' />";
				    }
				    if(isDeleteNeeded) {
				    	html = html + "<input type='button' class='button "+key+"ZXYXZ"+val+"' value='Delete' onclick=deleteKeyValuePair(\'"+formId+"\',\'"+fieldId+"\',\'"+divId+"_"+i+"\',\'"+key+"\',\'"+val+"\') />";
				    }
				    html = html + "<br />";
				}
			}
			html = html + "</div>";
			addNewHtml = "<a href='#' onclick='addNewKeyValuePair(\""+divId+"_"+i+"\","+isKeyEditable+")'>Add New Key-Value Pair</a><br />";
			fullHtml = fullHtml + html;
			fullHtml = fullHtml + addNewHtml;
		}
		$("#"+divId).html(fullHtml);
	} else {
		var html = "";
		var addNewHtml = "";
		for (var key in json) {
			if (json.hasOwnProperty(key)) {
			    var val = json[key];
			    if(isKeyEditable) {
			    	html = html + "<input type='text' class='"+key+"ZXYXZ"+val+"' value='"+key+"' /><input type='text' class='"+key+"ZXYXZ"+val+"' id='"+key+"ZXYXZ"+val+"' value='"+val+"' />";
			    } else {
			    	html = html + "<label class='"+key+"ZXYXZ"+val+"'>"+key+"</label>"+"<input type='text' class='"+key+"ZXYXZ"+val+"' id='"+key+"ZXYXZ"+val+"' value='"+val+"' />";
			    }
			    if(isDeleteNeeded) {
			    	html = html + "<input type='button' class='button "+key+"ZXYXZ"+val+"' value='Delete' onclick=deleteKeyValuePair(\'"+formId+"\',\'"+fieldId+"\',\'"+divId+"\',\'"+key+"\',\'"+val+"\') />";
			    }
			    html = html + "<br />";
			}
		}
		addNewHtml = "<a href='#' onclick='addNewKeyValuePair(\""+divId+"\","+isKeyEditable+")'>Add New Key-Value Pair</a><br/>";
		$("#"+divId).html(html+addNewHtml);
	}
};

var addNewKeyValuePair = function(divId, isKeyEditable) {
	//html = html + "<input type='text' /><input type='text' /><br/>";
	$("#"+divId).append("<input type='text' /><input type='text' /><br/>");
	return html;
};

var deleteKeyValuePair = function(formId, fieldId, divId, key, val) {
	var jsonObj = {};
	$("#"+divId).children('input').each(function () {
		if(typeof $(this)[0].id != "undefined" && $(this)[0].id != "") {
			var id = $(this)[0].id;
			if(id != key+"ZXYXZ"+val) {
				var pair = id.split("ZXYXZ");
				jsonObj[pair[0]] = pair[1];
			} 
		}
		
	});
	$("."+key+"ZXYXZ"+val).hide();
	var json = JSON.stringify(jsonObj);
    $("#"+formId+" #"+fieldId).val(json);
};

var saveSimpleJson = function(divId, fieldId, formId) {
	var jsonObj = {};
	$("#"+divId).children('div').each(function () {
		var subArr = {};
		$(this).children('input').each(function () {
			if(typeof $(this)[0].id != "undefined") {
				var id = $(this)[0].id;
				//var id = $(this).attr( "id" );
				var pair = id.split("ZXYXZ");
				subArr[pair[0]] = pair[1];
			}
		});
		jsonObj.push(subArr);
	});
	var json = JSON.stringify(jsonObj);
	$("#"+formId+" #"+fieldId).val(json);
};


