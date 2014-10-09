/**
 * 
 */

function getSelectBoxWithOptionSelected(id,options,name,selectedOption){
	var select = document.createElement("select");
	select.id = id;
	select.name = name;
	if(selectedOption != ""){
		var option = document.createElement("option");
		option.value = selectedOption;
		$(option).html(selectedOption);
		select.appendChild(option);
	}
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