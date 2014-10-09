package com.ail.creyate.generic_spring.utils.aws;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;

public class AWSImageUtils {
	/*
     * This method uploads Images from your local directory into S3 bucket
     * awsFolder - Defines the folder in S3 Bucket into which the data is to be pushed
     * */
	
	 public static void uploadImagesFromLocalDirectoryToBucket(String localDirectory,String bucketName, String awsFolder){
		 AWSUtils.uploadObjectsFromLocalDirectoryToBucket(localDirectory, bucketName, awsFolder);	
	 }
	 
	 /*
	     *This method downloads Images from an S3 bucket to the local directory
	     * awsFolder - Defines the folder inside S3 Bucket from which the data is to be pulled
	 */
	 
	 public static void downloadImagesFromBucketToLocalDirectory(String localDirectory, String bucketName, String awsFolder){
		 AWSUtils.downloadObjectsFromBucketToLocalDirectory(localDirectory, bucketName, awsFolder);
	 }
	 
	 /*
	     * This method uploads a particular Image from localDirectory to S3 bucket
	     * 
	 */
	 
	 public static PutObjectResult uploadImage(String bucketName, String imageKey, String sourceFilePath) {
		 return AWSUtils.uploadObject(bucketName, imageKey, sourceFilePath);
	 }

	 /*
	     * This method downloads a particular Image from S3 into targetFilePath
	     * $ImageKey - is the name of the Image to be downloaded
	 */
	 
	 public static void  downloadImageToAFile(String bucketName, String imageKey,String targetFilePath){
		 AWSUtils.downloadObjectToAFile(bucketName, imageKey, targetFilePath);
	 }
	 
	 public static void uploadFileList(String bucketName, String awsFolder, File directory, List<File>files){
		 AWSUtils.uploadFileListFromLocalDirectoryToBucket(bucketName, awsFolder, directory, files);
	 }
	 
	 public static boolean isValidFile(String bucketName, String awsPath){
		 return AWSUtils.isValidFile(bucketName, awsPath);
	 }
	
	 public static void copyFile(String sourceBkt, String srcKey, String destBkt, String destKey){
		 AWSUtils.copyObject(sourceBkt, srcKey, destBkt, destKey);
	 }
	 
	 public static ObjectListing listObjects(String bucketName, String prefix){
		 return AWSUtils.getObjects(bucketName, prefix);
	 }

}
