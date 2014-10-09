var addFileUploadWidget = function(divId,type,url,extraElements){
	//<form id="ImagesUploadForm" action="/generic_spring/garments/<%=garmentType%>/products/<%=id%>/images/uploadData" enctype="multipart/form-data" method="post">
	var form = document.createElement("form");
	form.action = url;
	form.enctype = "multipart/form-data";
	form.id = "ImagesUploadForm";
	form.method = "post";
	
	
	if(typeof extraElements == 'object'){
		for(var i=0; i < extraElements.length; i++){
			form.appendChild(extraElements[i]);
		} 
	}
	
	var input = document.createElement("input");
	input.type = "file";
	input.name = "file";
	input.id = "ImagesFileUpload";
	input.multiple = "multiple";
	
	form.appendChild(input);
	//<input type="button" id="uploadButton" value="Upload"/>
	input  = document.createElement("input");
	input.type = "button";
	input.id = "uploadButton";
	input.value = "Upload";
	input.className = "button";
	
	form.appendChild(input);
	
	$('#'+divId).append(form);
};



var onfileChange = function (id,swatchType){
	var fileName = $('#'+id).val();
	if(fileName == null || fileName.length == 0){
		return false;
	}
	return true;
};

function convertName(name,dl1,dl2){
	var parts = name.split(dl1);
	var final_name = "";
	for(var i=0;i<parts.length;i++){
		if(final_name.length == 0){
			final_name = parts[i];
		}
		else{
			final_name +=dl2 + parts[i];
		}
	}
	return final_name;
}

function getButton(val,type,disabled){
	var input = document.createElement("input");
	input.type = "button";
	input.name = name;
	input.value = val;
	input.id = type+"-statusbutton";
	input.disabled = disabled;
	if(disabled){
		input.className = "button-inactive";
	}
	else{
		input.className = "button";
	}
	return input;
}

function getCheckbox(className,id,checked){
	var input = document.createElement("input");
	input.type = "checkbox";
	input.className = className;
	input.id = id;
	if(checked){
		$(input).prop('checked',true);
	}
	return input;
}

function getTextBox(className, id, value,disabled,name){
	var input = document.createElement("input");
	input.type = "text";
	input.className = className;
	input.name = name;
	input.id = id;
	input.disabled = disabled;
	return input;
}

function getSelectBox(id,options,name){
	var select = document.createElement("select");
	select.id = id;
	select.name = name;
	for(var key in options){
		var option = document.createElement("option");
		option.value = key;
		$(option).html(options[key]);
		select.appendChild(option);
	}
	return select;
}


function getSelectBoxWithOptionSelected(id,options,name,selectedOption){
	var select = document.createElement("select");
	select.id = id;
	select.name = name;
	//if(selectedOption != ""){
		var option = document.createElement("option");
		option.value = selectedOption;
		$(option).html(selectedOption);
		select.appendChild(option);
	//}
	for(var key in options){
		if(options[key] != selectedOption){
			option = document.createElement("option");
			option.value = options[key];
			$(option).html(options[key]);
			select.appendChild(option);
		}
	}
	return select;
}

function getMultiSelectBox(id,options,name,selectedOptions){
	var select = document.createElement("select");
	select.id = id;
	select.name = name;
	var option = document.createElement("option");
	option.value = selectedOption;
	$(option).html(selectedOption);
	select.appendChild(option);
	for(var key in options){
		if(options[key] != selectedOption){
			option = document.createElement("option");
			option.value = options[key];
			$(option).html(options[key]);
			select.appendChild(option);
		}
	}
	return select;
}

function validateNumbers(){
	var numberPatt = new RegExp("^[0-9+.]+$","g");
	var numberElements = document.getElementsByClassName("number");
	for(var i = 0; i < numberElements.length; i++){
		var value = numberElements[i].value;
		var name = numberElements[i].name;
		var match = numberPatt.exec(value);
		if(match == null){
			alert(name+" should be a number");
			return false;
		}
	}
	return true;
}
