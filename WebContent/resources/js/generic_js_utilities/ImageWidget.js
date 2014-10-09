var updateStatus = function(status,version){
	$('#updateRenderStatusForm #version').val(version);
	$('#updateRenderStatusForm #status').val(status);
	$('#updateRenderStatusForm').submit();
};

var onfileChange = function (id){
	var fileName = $('#'+id).val();
	if(fileName == null || fileName.length == 0){
		return false;
	}
	return true;
};

function imageExists(url) {
	  var img = new Image();
	  img.onload = function() { callback(true); };
	  img.onerror = function() { callback(false); };
	  img.src = url;
}

function saveData(ImagesDivId,formId,approvedImagesId){
	var ImagesDiv = document.getElementById(ImagesDivId);
	var listOfContrastFabricElements =ImagesDiv.getElementsByTagName("input");
	var jsonOfApprovedImages = {};
	var len = listOfContrastFabricElements.length;
	
	for(var i=0; i < len;i++){
		var element = listOfContrastFabricElements[i];
		if(element.type == 'checkbox'){
			var id = element.id;
			var classname = element.className;
			if(classname != "primaryImage"){
				if($(element).prop("checked")){
					if(jsonOfApprovedImages[classname] == null){
						jsonOfApprovedImages[classname] = {};
					}
					jsonOfApprovedImages[classname][id] = "A";
				}
				else if(!$(element).prop("checked")){
					if(jsonOfApprovedImages[classname] == null){
						jsonOfApprovedImages[classname] = {};
					}
					jsonOfApprovedImages[classname][id] = "P";
				} 
			}
		}
		
	}

	$(approvedImagesId).val(JSON.stringify(jsonOfApprovedImages)); //jsonOfApprovedImages.toString());
	$('#'+formId).submit();
	
}
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
function getButton(val,id,disabled){
	var input = document.createElement("input");
	input.type = "button";
	input.value = val;
	input.id = id;
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
function getStatusElement(status,version,type){
	var element = null;
	if(status == "P"){
		if(type == "repeatable_swatch"){
			element = getButton("Send for Rendering", type+"-statusbutton", false);
			element.addEventListener("click",function(){ updateStatus("new", version); },false);
		}
		else{
			element = getCheckbox(type, version, false);
		}
	}
	else if(status == "new"){
		if(type == "repeatable_swatch"){
			element = getButton("Sent for Rendering", type+"-statusbutton", true);
		}
	}
	else if(status == "InProgress" || status == "rendered-1500"){
		element = getButton("Rendering In Progress", type+"-statusbutton", true);
	}
	else if(status == "rendered-610"){
		element = getButton("Rendering Done", type+"-statusbutton", true);
	}
	else if(status == "Done"){
		element = getCheckbox(type, version, false);
	}
	else if(status == "A"){
		element = getCheckbox(type, version, true);
	}
	
	return element;
}


function addImageDiv(type,garmentType,id,version,bucketName,status,extension, awsDomainName, itemType , primaryImage){
		var ImageDiv = document.createElement("div");
		ImageDiv.setAttribute("class",type+" imagediv");
 		var tbl = document.createElement("table");
 		tbl.className = "imageContainer";
 		var tr1 = document.createElement("tr");
 		var td1 = document.createElement("td");
 		
 		tr1.className = type+"-title";
 		
 		var span = document.createElement('span');
 		span.textContent = convertName(type,"_"," ")+" "+version;
		td1.appendChild(span);
		tr1.appendChild(td1);
		
		var tr2 = document.createElement("tr");
 		var td2 = document.createElement("td");
 		tr2.className = type+"-image";
 		
 		var elementDiv = document.createElement("div");
 		elementDiv.id = garmentType+"-"+type+"_"+id+"_"+version+"-element";
 		
 		var primaryImgDiv = document.createElement("div");
 		primaryImgDiv.id = garmentType+"-"+type+"_"+id+"_"+version+"-primaryImageDiv";
 		
 		var ImageUrl = "";
 		var displayUrl = "";
 		var width = "150px";
 		var height = "150px";
 		
 		if(itemType == "products"){
 			ImageUrl = awsDomainName+"/products/"+garmentType+"/"+id+"/Large_ipad/"+type+"/"+type+"_"+id+"_"+ version +"."+extension;
 			displayUrl = awsDomainName+"/products/"+garmentType+"/"+id+"/Medium/"+type+"/"+type+"_"+id+"_"+ version +"."+extension;
 			height = "200px";
 		}
 		else if(itemType == "fabrics"){
 			ImageUrl = awsDomainName+"/fabrics/"+garmentType+"/AllSwatches/"+id+"/"+type+"_"+id+"_"+ version +"."+extension;
 			displayUrl = ImageUrl;
 			primaryImgDiv.style.display = "none";
 		}
 		else if(itemType == "trims"){
 			ImageUrl = awsDomainName+"/trims/"+garmentType+"/"+id+"/"+type+"/"+type+"_"+id+"_"+ version +"."+extension;
 			displayUrl = ImageUrl;
 			primaryImgDiv.style.display = "none";
 		}
 		else if(itemType == "photoshoot_images"){
 			ImageUrl = awsDomainName+"/photoshoot_images/"+garmentType+"/"+id+"/original/"+type+"/"+type+"_"+id+"_"+ version +"."+extension;
 			displayUrl = awsDomainName+"/photoshoot_images/"+garmentType+"/"+id+"/Thumbnail/"+type+"/"+type+"_"+id+"_"+ version +".jpg";
 			primaryImgDiv.style.display = "none";
 			width = "100px";
 			height = "100px";
 		}
 		
 		var ImageLink = document.createElement("a");
 		ImageLink.setAttribute("id",type+"_"+id+"_"+version+"-img");
 		ImageLink.setAttribute("target", "_blank");
 		ImageLink.setAttribute("href", ImageUrl );
 		var ImgElement = document.createElement("img");
 		ImgElement.setAttribute("showApprove", true);
 		ImgElement.setAttribute("src", displayUrl);
		ImgElement.setAttribute("width" ,width);
		ImgElement.setAttribute("height",height);
		ImgElement.setAttribute("onerror", "this.src = '/generic_spring/resources/images/wait.jpeg';$('#'+'"+elementDiv.id+"').hide();$('#'+'"+primaryImgDiv.id+"').hide();");
		ImageLink.appendChild(ImgElement);
 		
		td2.appendChild(ImageLink);
 		tr2.appendChild(td2);
 		
 		var tr3 = document.createElement("tr");
 		var td3 = document.createElement("td");
 		tr3.className = type+"-check";
 		
 		var element = getStatusElement(status,version, type);
 	
 		if(element.type == "checkbox"){
 			if(!$(element).prop("checked")){
 				$(elementDiv).append('<span>Approve</span>');
 			}
 			else if($(element).prop("checked")){
 				$(elementDiv).append('<span>Approved</span>');
 			}
 		}
		elementDiv.appendChild(element);
 		td3.appendChild(elementDiv);
 		tr3.appendChild(td3);
 		
 		var tr4 = document.createElement("tr");
 		var td4 = document.createElement("td");
 		
 		$(primaryImgDiv).append("<span>Primary Image</span>");
	
 		var primaryImgId = type+"_"+id+"_"+version;
 		var element = null;
 		if(primaryImage == primaryImgId){
 			element = getCheckbox("primaryImage", primaryImgId , true);
 		}
 		else{
 		 	element = getCheckbox("primaryImage", primaryImgId , false);
 		}
 		primaryImgDiv.appendChild(element);
 		td4.appendChild(primaryImgDiv);
 		tr4.appendChild(td4);
 		
 		tbl.appendChild(tr1);
 		tbl.appendChild(tr4);
 		tbl.appendChild(tr2);
 		tbl.appendChild(tr3);
 		
 		
 		$(ImageDiv).append(tbl);
 		return ImageDiv;
	
}
function addImageWidget(imagesDiv, garmentType, type, ImagesJson, approvedImagesJson, id, bucketName, awsDomainName, itemType, primaryImage)
{	
	if(ImagesJson[type] != null){
		var div = document.createElement("div");
		div.setAttribute("id", type+"-div");
		div.style = "overflow-x:scroll";
		var table = document.createElement("table");
		table.setAttribute("id", type+"-table");
		var tr = document.createElement("tr");
		if(approvedImagesJson[type] == null){
			approvedImagesJson[type] = {};
		}
		var maxVersion = ImagesJson[type]['max_v'];
		var extension = ImagesJson[type]['ext'];
		for(var i=0;i<=maxVersion; i++){
		 	var td=document.createElement("td");
		 	var status = approvedImagesJson[type][i.toString()] != null ? approvedImagesJson[type][i.toString()] : "P";
		 	td.appendChild(addImageDiv(type, garmentType, id, i , bucketName, status ,extension, awsDomainName, itemType, primaryImage));
		 	
		 	tr.appendChild(td);
		}
		table.appendChild(tr);
		div.appendChild(table);
		$('#'+imagesDiv).append(div);
	}
}