package com.derrick.covington.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Resource
public class ExcelDataStore implements DataStore{
	private XSSFWorkbook workbook;
	private File xlsxFile;

	public ExcelDataStore(String fileName) {
		xlsxFile = new File(fileName);
	}
	
	public List<Map<String, String>> getData() {
		List<Map<String,String>> data = new ArrayList<Map<String, String>>();
		 FileInputStream inputStream;
		try {
			//Creating input stream
			 inputStream= new FileInputStream(xlsxFile);
			              
			 //Creating workbook from input stream
			 workbook = new XSSFWorkbook(inputStream);
		
		
			 XSSFSheet sheet = workbook.getSheetAt(0);
		
        int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			for (int index = firstRow + 1; index <= lastRow; index++) {
			    Row row = sheet.getRow(index);
			    Map<String,String> address = new HashMap<String,String>();
			    address.put("ipAddress", row.getCell(0).getStringCellValue());
			    address.put("status", row.getCell(1).getStringCellValue());
			    data.add(address);
			}
			inputStream.close();
		 }
		 catch(Exception e) {
			 
			 throw new RuntimeException(e);
		 }
		return data;
	}

	public boolean assignIp(String ip) {
		FileInputStream inputStream;
		try {
			//Creating input stream
			inputStream= new FileInputStream(xlsxFile);
			              
			 //Creating workbook from input stream
			 workbook = new XSSFWorkbook(inputStream);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		for (int index = firstRow + 1; index <= lastRow; index++) {
			Row row = sheet.getRow(index);
		    if(row.getCell(0).getStringCellValue().equals(ip)) {
		    	row.getCell(1).setCellValue("In Use");
		    	break;
		    }
		}
		inputStream.close();
		
		FileOutputStream outputStream = new FileOutputStream("src/main/resources/IpAddressDataStore.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean releaseIp(String ip) {
		FileInputStream inputStream;
		try {
			//Creating input stream
			inputStream= new FileInputStream(xlsxFile);
			              
			 //Creating workbook from input stream
			 workbook = new XSSFWorkbook(inputStream);
		
			 XSSFSheet sheet = workbook.getSheetAt(0);
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		for (int index = firstRow + 1; index <= lastRow; index++) {
			Row row = sheet.getRow(index);
		    if(row.getCell(0).getStringCellValue().equals(ip)) {
		    	row.getCell(1).setCellValue("Available");
		    	break;
		    }
		}
		inputStream.close();
		
		FileOutputStream outputStream = new FileOutputStream("src/main/resources/IpAddressDataStore.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createNewRecords(Map<String,String> records) {
		
		FileInputStream inputStream;
		try {
			//Creating input stream
			inputStream= new FileInputStream(xlsxFile);
			              
			 //Creating workbook from input stream
		workbook = new XSSFWorkbook(inputStream);
	
					 XSSFSheet sheet = workbook.getSheetAt(0);
         int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			for (int index = firstRow + 1; index <= lastRow; index++) {
			    Row row = sheet.getRow(index);
			    if(records.get(row.getCell(1).getStringCellValue()) != null) {
			    	records.remove(row.getCell(1).getStringCellValue());
			    }
			}
			for(String address : records.keySet()) {
				Row extraRow = sheet.createRow(lastRow + 1);
				Cell addressCell = extraRow.createCell(0);
				addressCell.setCellValue(address);
				Cell usageCell = extraRow.createCell(1);
				usageCell.setCellValue(records.get(address));
				lastRow++;
			}
			inputStream.close();
			
			FileOutputStream outputStream = new FileOutputStream("src/main/resources/IpAddressDataStore.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
		}
	catch(Exception e) {
		e.printStackTrace();
		return false;
	}
		 return true;
	}


}
