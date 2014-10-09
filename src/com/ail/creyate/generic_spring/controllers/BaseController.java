package com.ail.creyate.generic_spring.controllers;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

import com.ail.creyate.generic_spring.commons.EmailMessage;
import com.ail.creyate.generic_spring.db.beans.LogsBean;
import com.ail.creyate.generic_spring.models.EmailServiceModel;
import com.ail.creyate.generic_spring.models.LogsModel;
import com.ail.creyate.generic_spring.utils.ApplicationProperties;
import com.ail.creyate.generic_spring.utils.Constants;
import com.ail.creyate.generic_spring.utils.LoggingUtil;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

@Controller
public class BaseController {
	
	
	@Autowired(required=true)
	protected  LogsModel logsModel;
	
	@Autowired(required=true)
	protected EmailServiceModel emailServiceModel;
	
	public<T> void log(HttpServletRequest request, T oldBean, T newBean, String pageId) {
		try{
			if(request.getSession() != null){
				Object nameObj = request.getSession().getAttribute("name");
				Object emailObj = request.getSession().getAttribute("email");
				if(nameObj != null && emailObj != null && nameObj instanceof String && emailObj instanceof String){
					String changedFields = LoggingUtil.getChangedFields(oldBean, newBean);
					String id = LoggingUtil.getId(newBean);
					logsModel.log(changedFields, pageId, (String)nameObj, (String)emailObj,id);
				}
			}
		}
		catch(Exception e){
			return;
		}
		return;
	}
	
	protected LogsBean getLastModifiedUserBean(String type, String itemId){
		return logsModel.getLastUpdatedUserInfo(type, itemId);
	}
	
	
	public JSONObject uploadProductImagesToServer(String garmentType,String productId,String swatchType, int version,String fileExtension,String imageFileName, String type){
		JSONObject result = new JSONObject();
		try{
			result.put("success",false);
			
			String uploadDirectory = ApplicationProperties.getProperty("upload.directory")+File.separator+"images";
				
			String originalResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/original/"+swatchType;
			result.put("filePath", originalResolutionFolderPath + File.separator + swatchType+"_"+productId+"_"+ version +"."+ fileExtension);
		
			File folder = new File(originalResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String largeDesktopResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.HIGH_DESKTOP_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(largeDesktopResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String largeIpadResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.HIGH_IPAD_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(largeIpadResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String zoomResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.ZOOM_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(zoomResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String mediumResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.MEDIUM_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(mediumResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String lowResolutionFolderPath = uploadDirectory+"/"+type+"/"+garmentType+"/"+productId+"/"+Constants.RESOLUTION.THUMBNAIL_RESOLUTION.getName()+"/"+swatchType;
			folder = new File(lowResolutionFolderPath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			File srcFile = new File(imageFileName);
			File destFile = new File(originalResolutionFolderPath + File.separator + swatchType+"_"+productId+"_"+ version +"."+ fileExtension );
			FileUtils.copyFile(srcFile, destFile);
			
			result.put("success", true);
		}
		catch(Exception e){
			return result;
		}
		return result;
	}
	
	public JSONObject uploadFabricImagesToServer(String garmentType,String fabricId,String swatchType, int version,String fileExtension,String imageFileName){
		JSONObject result = new JSONObject();
		JSONArray files = new JSONArray();
		try{
			result.put("success",false);
			
			String uploadDirectory = ApplicationProperties.getProperty("upload.directory")+File.separator+"images";
			
			String allSwatchesFolder = uploadDirectory+"/fabrics/"+garmentType+"/AllSwatches/"+fabricId;
			File folder = new File(allSwatchesFolder);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			File srcFile = new File(imageFileName);
			File destFile = new File(allSwatchesFolder + File.separator +swatchType+"_"+fabricId+"_"+ version +"."+ fileExtension);
			FileUtils.copyFile(srcFile, destFile);
			
			files.put(destFile);
			
			if(swatchType.equals(Constants.SWATCH_TYPES.CHOICE.getName()) || swatchType.equals(Constants.SWATCH_TYPES.RENDERABLE.getName())){
				String swatchFolder = uploadDirectory +"/fabrics/"+garmentType+"/"+swatchType;
				
				folder = new File(allSwatchesFolder);
				if(!folder.exists()){
					folder.mkdirs();
				}
				
				srcFile = new File(imageFileName);
				destFile = new File(swatchFolder + File.separator +swatchType+"_"+fabricId+"_"+ version +"."+ fileExtension);
				FileUtils.copyFile(srcFile, destFile);
				files.put(destFile);
				
			}
			if(swatchType.equals(Constants.SWATCH_TYPES.RENDERABLE.getName())){
				String textureFolder = uploadDirectory + File.separator + garmentType+"/texture";
				
				folder = new File(textureFolder);
				if(!folder.exists()){
					folder.mkdirs();
				}
				
				srcFile = new File(imageFileName);
				destFile = new File(textureFolder + File.separator + fabricId+"_"+ version +"."+ fileExtension);
				FileUtils.copyFile(srcFile, destFile);
				result.put("textureFile", destFile);
				//AWSImageUtils.uploadImage(bucketName, awsFileName, imageFileName);
			}
			result.put("files", files);
			result.put("success", true);
			return result;
		}
		catch(Exception e){
			return result;
		}
	}
	
	public JSONObject uploadTrimImagesToServer(String garmentType, String trimId,String swatchType, int version,String fileExtension,String imageFileName){
		JSONObject result = new JSONObject();
		JSONArray files = new JSONArray();
		try{
			result.put("success", false);
			String uploadDirectory = ApplicationProperties.getProperty("upload.directory")+File.separator+"images";
			String trimsFolder = uploadDirectory+"/trims/"+ garmentType + File.separator + trimId + File.separator + swatchType;
			File folder = new File(trimsFolder);
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			File srcFile = new File(imageFileName);
			File destFile = new File(trimsFolder + File.separator +swatchType+"_"+trimId+"_"+ version +"."+ fileExtension);
			FileUtils.copyFile(srcFile, destFile);
			files.put(destFile);
			
			result.put("files", files);
			result.put("success", true);
			
			return result;
		}
		catch(Exception e){
			return result;
		}
	}
	
	public void sendPlainEmail(String fromAddress, String fromPassword, String toAddress, String subject, String message) {
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) emailServiceModel.getJavaMailService();
		mailSender.setUsername(fromAddress);
		mailSender.setPassword(fromPassword);
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toAddress);
        email.setSubject(subject);
        email.setText(message);
		mailSender.send(email);
		
	}
	
	public void sendHTMLEmail(String fromAddress, String fromPassword, String[] toAddressList, String subject, String message, String[] ccList, String[] bccList) {
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) emailServiceModel.getJavaMailService();
		mailSender.setUsername(fromAddress);
		mailSender.setPassword(fromPassword);
		
		EmailMessage emailMessage = new EmailMessage(fromAddress, fromPassword, toAddressList, subject, message, ccList, bccList);
		mailSender.send(emailMessage.getMimeMessage());
	}
	
}
