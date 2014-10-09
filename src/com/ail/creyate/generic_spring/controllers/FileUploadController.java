package com.ail.creyate.generic_spring.controllers;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ail.creyate.generic_spring.utils.ApplicationProperties;
import com.ail.creyate.generic_spring.utils.Constants;
import com.ail.creyate.generic_spring.utils.ImageMagickUtils;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

@Controller
@RequestMapping(value="/garments")
public class FileUploadController extends BaseController{


	public JSONObject uploadProductPhotoshootImagesToServer(String garmentType, String productId,String swatchType, int version,String fileExtension,String imageFileName, String type){
		JSONObject result = new JSONObject();
		try{
			result.put("success",false);
			JSONArray files = new JSONArray();
			String uploadDirectory = ApplicationProperties.getProperty("upload.directory")+File.separator+"images";
				
			String originalResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/original/"+swatchType;
			String originalFilePath = originalResolutionFolderPath + File.separator + swatchType+"_"+productId+"_"+ version +"."+ fileExtension;
			//result.put("filePath", originalFilePath);
			
			File folder = new File(originalResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String displayFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.THUMBNAIL_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(displayFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			String displayFilePath = displayFolderPath + File.separator + swatchType+"_"+productId+"_"+ version +".jpg";
			File srcFile = new File(imageFileName);
			File destFile = new File(originalFilePath);
			FileUtils.copyFile(srcFile, destFile);
			
			boolean isThumbnailReduced = ImageMagickUtils.convertToJpg(originalFilePath, displayFilePath, Constants.RESOLUTION.THUMBNAIL_RESOLUTION.getWidth(), Constants.RESOLUTION.THUMBNAIL_RESOLUTION.getHeight());

			if(isThumbnailReduced){
				files.put(originalFilePath);
				files.put(displayFilePath);
				
			}
			result.put("files", files);
			result.put("success", true);
		}
		catch(Exception e){
			return result;
		}
		return result;
	}
}
