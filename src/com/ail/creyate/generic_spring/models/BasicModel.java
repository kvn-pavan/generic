package com.ail.creyate.generic_spring.models;

import org.springframework.stereotype.Service;

import com.amazonaws.util.json.JSONObject;

@Service
public class BasicModel {
	
	private int getMaxVersion(JSONObject ImagesJson){
		try{
			if(ImagesJson != null && ImagesJson.length() > 0){
				if(ImagesJson.has("max_v")){
					return ImagesJson.getInt("max_v");
				}
				return -1;
			}
			else{
				return -1;
			}
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public int getMaxVersionForType(String  ImagesJsonString, String swatchType){
		try{
			if(ImagesJsonString == null || ImagesJsonString.length() == 0){
				return -1;
			}
			else{
				JSONObject imagesJson = new JSONObject(ImagesJsonString);
				if(!imagesJson.has(swatchType)){
					return -1;
				}
				return getMaxVersion(imagesJson.getJSONObject(swatchType));
			}
		}
		catch(Exception e){
			return -1;
		}
	}
	

	public String updateMaxVersionForType(String imagesJsonString, String id, String swatchType, int val,String extension){
		try{
			if(imagesJsonString == null || imagesJsonString.length() == 0){
				imagesJsonString = new JSONObject().toString();
			}
			JSONObject imagesJson = new JSONObject(imagesJsonString);
			if(!imagesJson.has(swatchType)){
				JSONObject obj = new JSONObject();
				obj.put("fn", id);
				if(extension != null){
					obj.put("ext", extension);
				}
				imagesJson.put(swatchType,obj);
			}
			JSONObject swatchJson = imagesJson.getJSONObject(swatchType);
			swatchJson.put("fn", id);
			swatchJson.put("ext", extension);
			swatchJson.put("max_v", val);
			imagesJson.put(swatchType, swatchJson);
			
			return imagesJson.toString();
		}
		catch(Exception e){
			return imagesJsonString;
		}
		
	}
	
	
}
