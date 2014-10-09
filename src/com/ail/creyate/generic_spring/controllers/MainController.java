package com.ail.creyate.generic_spring.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ail.creyate.generic_spring.models.LogsModel;
import com.ail.creyate.generic_spring.utils.ApplicationProperties;
import com.ail.creyate.generic_spring.utils.Constants;
import com.ail.creyate.generic_spring.utils.DateUtils;
import com.ail.creyate.generic_spring.utils.aws.AWSUtils;

@Controller
@RequestMapping(value="")
public class MainController {
	
	@Autowired(required=true)
	protected  LogsModel logsModel;

	@RequestMapping(value="/products/uploadMultiple",method=RequestMethod.GET)
	public String uploadMultiple(HttpServletRequest request, HttpServletResponse response) {
		return Constants.UPLOAD_MULTIPLE_PRODUCTS_IMAGES; 
	}
	
	@RequestMapping(value="/backup/{dbName}", method=RequestMethod.GET)
	public void takeDBdump(HttpServletRequest request, HttpServletResponse response, @PathVariable String dbName) throws IOException, InterruptedException{
		String uploadDirParentPath = new File(ApplicationProperties.getProperty("upload.directory")).getPath();


		/*NOTE: Creating Database Constraints*/
		String dbUser = ApplicationProperties.getProperty("database.local.username");
		String dbPass = ApplicationProperties.getProperty("database.local.password");

		/*NOTE: Creating Path Constraints for folder saving*/
		/*NOTE: Here the backup folder is created for saving inside it*/
		String folderPath = uploadDirParentPath +File.separator +"backup";

		/*NOTE: Creating Folder if it does not exist*/
		File f1 = new File(folderPath);
		f1.mkdirs();

		String fileName = "backup_"+dbName+"_"+DateUtils.getCurrentDateTimeINYYYYMMDDHHmmss().replace(" ", "_")+".sql";
		/*NOTE: Creating Path Constraints for backup saving*/
		/*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
		String savePath = File.separator + uploadDirParentPath + File.separator +"backup" + File.separator + fileName;

		/*NOTE: Used to create a cmd command*/
		String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

		/*NOTE: Executing the command here*/
		Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
		int processComplete = runtimeProcess.waitFor();

		/*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
		if (processComplete == 0) {
			System.out.println("Backup Complete");
			AWSUtils.uploadObject(ApplicationProperties.getProperty("aws.backup_bucket"), "cms_db_backups/"+fileName, savePath);
		} else {
			System.out.println("Backup Failure");
		}
	}

	
}







