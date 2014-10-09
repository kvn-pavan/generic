package com.ail.creyate.generic_spring.utils.aws;

import com.ail.creyate.generic_spring.utils.ApplicationProperties;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSClient {
	
	public static AmazonS3 client = null;
	
	private static Regions region = Regions.AP_SOUTHEAST_1;
	
	public synchronized static AmazonS3 getClient() {
		if(client == null){
			AWSCredentials myCredentials = new BasicAWSCredentials(ApplicationProperties.getProperty("aws.key"), ApplicationProperties.getProperty("aws.secret"));
			client = new AmazonS3Client(myCredentials);
			client.setRegion(Region.getRegion(region));
		}
		return client;
	}

}
