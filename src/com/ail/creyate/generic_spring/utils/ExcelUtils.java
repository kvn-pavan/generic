package com.ail.creyate.generic_spring.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
		
	public final static String HEADER_SEPARATOR = "$";
	
	public static List<Map<String, String>> convertToListofMapsHSSF(String filePathToxls) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); 
		List<String> headers = new ArrayList<String>();
		
		try {
		     
		    FileInputStream file = new FileInputStream(new File(filePathToxls));
		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    
		    //Get the row headers-meta
		    if(rowIterator.hasNext()) {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) {
		            Cell cell = cellIterator.next();
		            headers.add(cell.getStringCellValue());
		        }
		    }
		    
		    //Get the actual row headers
		    if(rowIterator.hasNext()) {
		    	Row row = rowIterator.next();
		    	for(int i = 0; i < headers.size(); i++) {
		    		headers.set(i, headers.get(i)+HEADER_SEPARATOR+row.getCell(i).getStringCellValue());
		    	}
		    }
		    
		    
		    //Add the data
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        Map<String, String> map = new LinkedHashMap<String, String>();
		        if(row.getPhysicalNumberOfCells() > 0) {
			        for(int i = 0; i < headers.size(); i++) {
			        	Cell cell = row.getCell(i);
			        	if(cell != null) {
				        	switch(cell.getCellType()) {
				                case Cell.CELL_TYPE_BOOLEAN:
				                	map.put(headers.get(i), String.valueOf(cell.getBooleanCellValue()));
				                    break;
				                case Cell.CELL_TYPE_NUMERIC:
				                	map.put(headers.get(i), String.valueOf(cell.getNumericCellValue()));
				                    break;
				                case Cell.CELL_TYPE_STRING:
				                	map.put(headers.get(i), cell.getStringCellValue());
				                    break;
				                default:
				                	map.put(headers.get(i), "");
				                	break;
				        	}
			        	} else {
			        		map.put(headers.get(i), "");
			        	}
			    	}
			        list.add(map);
		        }
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, String>> convertToListofMapsXSSF(String filePathToxls) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); 
		
		List<String> headers = new ArrayList<String>();
		
		try {
		     
		    FileInputStream file = new FileInputStream(new File(filePathToxls));
		    //Get the workbook instance for XLS file 
		    XSSFWorkbook workbook = new XSSFWorkbook (file);
		 
		    //Get first sheet from the workbook
		    XSSFSheet sheet = workbook.getSheetAt(0);
		    
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    
		    //Get the row headers-meta
		    if(rowIterator.hasNext()) {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) {
		            Cell cell = cellIterator.next();
		          //adding code to prevent adding blank header
		            if(cell.getStringCellValue().equals(""))
		            {
		            	break;
		            }
		            //headers.add(cell.getStringCellValue());
		            headers.add(getStringFromCell(cell));
		            
		        }
		    }
		    
		    //Get the actual row headers
		    if(rowIterator.hasNext()) {
		    	Row row = rowIterator.next();
		    	for(int i = 0; i < headers.size(); i++) {
		    		//headers.set(i, headers.get(i)+HEADER_SEPARATOR+row.getCell(i).getStringCellValue());
		    		headers.set(i, headers.get(i)+HEADER_SEPARATOR+getStringFromCell(row.getCell(i)));
		    		
		    	}
		    }
		    
		      int counter = 0;
		    //Add the data
		    while(rowIterator.hasNext()) {
		    	counter = counter + 1;
		    	
		    	Map<String, String> map = new LinkedHashMap<String, String>();
		        Row row = rowIterator.next();
		        
		        
		        if(row.getPhysicalNumberOfCells() > 0) {
		        	
		        	
		        	
		            //code to stop read 
		        	if(getStringFromCell(row.getCell(0)).equalsIgnoreCase("stop"))
		        	{break;}
		        	
			        for(int i = 0; i < headers.size(); i++) {
			        	Cell cell = row.getCell(i);
			        	if(cell != null) {
				        	switch(cell.getCellType()) {
				                case Cell.CELL_TYPE_BOOLEAN:
				                	map.put(headers.get(i), String.valueOf(cell.getBooleanCellValue()));
				                    break;
				                case Cell.CELL_TYPE_NUMERIC:
				                	map.put(headers.get(i), String.valueOf(cell.getNumericCellValue()).replaceAll("\\.0", ""));
				                    break;
				                case Cell.CELL_TYPE_STRING:
				                	map.put(headers.get(i), cell.getStringCellValue());
				                    break;
				                default:
				                	map.put(headers.get(i), "");
				                	break;
				        	}
			        	} else {
			        		map.put(headers.get(i), "");
			        	}
			        	
			    	}
			        list.add(map);
		        }
		    }
		    
		    
		   
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	public static String getStringFromCell(Cell cell){
		
		
		String value = "";
		if(cell != null) {
        	switch(cell.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                	value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                	value = String.valueOf(cell.getNumericCellValue()).replaceAll("\\.0", "");
                    break;
                case Cell.CELL_TYPE_STRING:
                	value = cell.getStringCellValue();
                    break;
                default:
                	
        	}
        	
    	}
		return value; 
		
		
	}
	
	public static void main(String[] args) {
		List<Map<String, String>> listOfMaps = convertToListofMapsXSSF("/home/surya/imagesupload/excels/Fabric_Upload_file.xlsx");
		for(int i = 0; i < listOfMaps.size(); i++) {
			Map<String, String> map = listOfMaps.get(i);
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				System.out.println(key+"--"+value);		
			}
		}
	}

}
