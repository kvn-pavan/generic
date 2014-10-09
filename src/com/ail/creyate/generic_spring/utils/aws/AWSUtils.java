package com.ail.creyate.generic_spring.utils.aws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.ail.creyate.generic_spring.utils.ApplicationProperties;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.ObjectMetadataProvider;
import com.amazonaws.services.s3.transfer.TransferManager;

public class AWSUtils {

	
	/*
     * This method uploads Objects from your local directory into S3 bucket
     * $awsFolder - Defines the folder in S3 Bucket into which the data is to be pushed
     * */
	
	private static ObjectMetadataProvider getObjMetaDataProvider(){
		ObjectMetadataProvider metadataProvider = new ObjectMetadataProvider() {
			
			@Override
			public void provideObjectMetadata(File arg0, ObjectMetadata metadata) {
				// TODO Auto-generated method stub
				metadata.setCacheControl("max-age="+ApplicationProperties.getProperty("s3.cache.expiresTime"));
				
			}
		};
		return metadataProvider;
	}
	
    public static void uploadObjectsFromLocalDirectoryToBucket(String localDirectory,String bucketName, String awsFolder){
    	AmazonS3 client = AWSClient.getClient();
    	TransferManager tmanager = new TransferManager(client);
    	tmanager.uploadDirectory(bucketName, awsFolder, new File(localDirectory), true, getObjMetaDataProvider());
    }

    public static void uploadFileListFromLocalDirectoryToBucket(String bucketName, String virtualDirectoryKeyPrefix, File directory, List<File> files ){
    	AmazonS3 client = AWSClient.getClient();
    	TransferManager tmanager = new TransferManager(client);
    	tmanager.uploadFileList(bucketName, virtualDirectoryKeyPrefix, directory, files, getObjMetaDataProvider());
    }
    
    /*
     *This method downloads Objects from an S3 bucket to the local directory
     * $awsFolder - Defines the folder inside S3 Bucket from which the data is to be pulled
     */
    
    public static void downloadObjectsFromBucketToLocalDirectory(String localDirectory, String bucketName, String awsFolder){
    	AmazonS3 client = AWSClient.getClient(); 
    	TransferManager tmanager = new TransferManager(client);
    	tmanager.downloadDirectory(bucketName, awsFolder, new File(localDirectory));
    }
    
    /*
     * This method uploads a particular Object from localDirectory to S3 bucket
     * */
    
    public static PutObjectResult uploadObject(String bucketName, String objectKey, String sourceFilePath){
    	ObjectMetadata objMetaData = new ObjectMetadata();
    	objMetaData.setCacheControl("max-age=1296000");
    	AmazonS3 client = AWSClient.getClient();
    //	PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, objectKey, new File(sourceFilePath)), objMetaData);
    	PutObjectResult result = null;
		try {
			result = client.putObject(bucketName, objectKey, new FileInputStream(sourceFilePath), objMetaData);
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }

    /*
     * This method downloads a particular Object from S3 into targetFilePath
     * $objectKey - is the name of the object to be downloaded
     * */
    
    public static void downloadObjectToAFile(String bucketName, String objectKey, String targetFilePath){
    	AmazonS3 client = AWSClient.getClient();
    	client.getObject(new GetObjectRequest(bucketName, objectKey), new File(targetFilePath));
    }
    
    public static void copyObject(String srcBkt, String srcKey, String destBkt, String destKey){
    	AmazonS3 client = AWSClient.getClient(); 
    	TransferManager tmanager = new TransferManager(client);
    	tmanager.copy(srcBkt, srcKey, destBkt, destKey);
    }
    
    public static boolean isValidFile(String bucketName,String path)  {
        boolean isValidFile = true;
        AmazonS3 s3 = AWSClient.getClient();
        try {
        	ObjectMetadata objectMetadata = s3.getObjectMetadata(bucketName, path);
        } catch (AmazonS3Exception s3e) {
            if (s3e.getStatusCode() == 404) {
            // i.e. 404: NoSuchKey - The specified key does not exist
                isValidFile = false;
            }
            else {
                throw s3e;    // rethrow all S3 exceptions other than 404   
            }
        }

        return isValidFile;
    }
    
    public static ObjectListing getObjects(String bucketName, String prefix){
    	AmazonS3 s3 = AWSClient.getClient();
    	return s3.listObjects(bucketName, prefix);
    }
    
}
