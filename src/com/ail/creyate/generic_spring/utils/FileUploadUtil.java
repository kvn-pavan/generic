package com.ail.creyate.generic_spring.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

/**
 * 
 * 
 * @author indupriya
 *
 */

public class FileUploadUtil {
	public static JSONObject uploadFile(HttpServletRequest request,String targetName){
		JSONObject result = new JSONObject();
		try {
			result.put("success", false); 
			String category = null;
			if(ServletFileUpload.isMultipartContent(request)){

				String uploadDirectory = ApplicationProperties.getProperty("upload.directory")+File.separator+targetName;
				File folder = new File(uploadDirectory);
				if(!folder.exists()){
					folder.mkdirs();
				}
				
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);
				JSONArray listOffiles = new JSONArray();
				for(FileItem item : multiparts){
					if(!item.isFormField()){
						String name = new File(item.getName()).getName();
						String extension = FilenameUtils.getExtension(name);
						String newFileName = uploadDirectory + File.separator + name;

						item.write( new File( newFileName));
						
						if(extension.equals("zip")){
							listOffiles = extractZipFile(newFileName,uploadDirectory+File.separator+FilenameUtils.getBaseName(name),listOffiles);
							result.put("fileNames",listOffiles);
						}	 
						else{
							listOffiles.put(newFileName);
							result.put("fileNames", listOffiles); 
						}
					}
					else{
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();
						if(fieldName.equals("swatchType")){	
							category = fieldValue;
						}
						result.put(fieldName, fieldValue);
					}
				}
				result.put("success", true); 
				return result;
			}else{
				return result;
			}


		}
		catch (Exception ex) {
			return result;
		}          
	}
	
	public static JSONArray extractZipFile(String zipFile,String destinationFolder,JSONArray listOfFiles){
		try{
			byte[] buffer = new byte[1024];
			File folder = new File(destinationFolder);
			if(!folder.exists()){
				folder.mkdir();
			}

			//get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));

			//get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			

			while(ze!=null){
				String fileName = ze.getName();
				String extension = FilenameUtils.getExtension(fileName);
				if(extension != null && extension.length()>0){

					File newFile = new File(destinationFolder + File.separator + fileName);


					//create all non exists folders
					//else you will hit FileNotFoundException for compressed folder

					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos = new FileOutputStream(newFile);             

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();   
					listOfFiles.put(destinationFolder + File.separator + fileName);
				}
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
			return listOfFiles;

		}
		catch(Exception e){
			return listOfFiles;
		}
	}
	
}
