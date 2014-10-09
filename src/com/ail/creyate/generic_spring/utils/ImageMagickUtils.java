package com.ail.creyate.generic_spring.utils;


public class ImageMagickUtils {
	public static boolean resizeImage(String srcPath,String destPath,int width,int height){
		try{
			Runtime aRT = Runtime.getRuntime(); 
			String command = "convert "+srcPath+" -resize "+width+"x"+height+" "+destPath;
			Process aProc = aRT.exec(command);
			int exitStatus = aProc.waitFor();
			if(exitStatus == 0){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	public static boolean convertToJpg(String srcPath, String destPath, int width, int height){
		try{
			Runtime aRT = Runtime.getRuntime(); 
			String command = "convert "+srcPath+" -resize "+width+"x"+height+" -background white -flatten "+destPath;
			Process aProc = aRT.exec(command);
			int exitStatus = aProc.waitFor();
			if(exitStatus == 0){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			return false;
		}
	}
}
