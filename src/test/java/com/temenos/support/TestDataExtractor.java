package com.temenos.support;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TestDataExtractor {

	private String csvFileName;

	List<LinkedHashMap <String, String>> allResults;

	public TestDataExtractor() {
	}

	public TestDataExtractor(String csvFileName) {
		allResults = new ArrayList<LinkedHashMap <String, String>>();
		this.csvFileName = csvFileName;
		readAllData();
	}
	
	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}
	
	/**
	 * Fetch Data from CSV
	 * 
	 * @return: Data from CSV as LinkedHashMap format
	 */
	public LinkedHashMap <String, String> readData(String testCaseId) {		
		for(int index=0; index < allResults.size(); index++) {
			LinkedHashMap <String, String> record = allResults.get(index);
			if(record.get("testCaseId").equals(testCaseId)) {
				return record;
			}
		}
		
		throw new Error("No test data available for Test case Id :"+  testCaseId);
	}

	/**
	 * Fetches All Data from CSV
	 * 
	 */
	private void readAllData() {		
        BufferedReader br = null;
        String line = null;
        String[] header = null;
        
        if(csvFileName.endsWith(".csv") || csvFileName.endsWith(".CSV")) {
        	csvFileName = csvFileName.replace(".csv", "").replace(".CSV", "");
        }
        String csvFilePath = "./src/main/resources/testdata/" + csvFileName + ".csv";
        
		try {
			br = new BufferedReader(new FileReader(csvFilePath));
			line = br.readLine();
			header = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

	        while ((line = br.readLine()) != null) {
	            if (!line.trim().isEmpty()) {
	            	LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);             
	                for (int index = 0; index < fields.length; index++) {
	                    result.put(header[index], fields[index]);                    
	                }                
	                allResults.add(result);
	            }
	        }
	        br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        if(allResults.isEmpty()) {
        	throw new Error("No testdata present in the CSV!");
        }        	
	}
}
