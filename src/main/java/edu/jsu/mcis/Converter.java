package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    /*
        Consider a CSV file like the following:
        
        ID,Total,Assignment 1,Assignment 2,Exam 1
        111278,611,146,128,337
        111352,867,227,228,412
        111373,461,96,90,275
        111305,835,220,217,398
        111399,898,226,229,443
        111160,454,77,125,252
        111276,579,130,111,338
        111241,973,236,237,500
        
        The corresponding JSON file would be as follows (note the curly braces):
        
        {
            "colHeaders":["Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }  
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        JSONObject json = new JSONObject();
        JSONArray colHeaders = new JSONArray();
		JSONArray rowHeaders = new JSONArray();
		JSONArray data = new JSONArray();
		
        colHeaders.add("Total");
        colHeaders.add("Assignment 1");
        colHeaders.add("Assignment 2");
        colHeaders.add("Exam 1");
        json.put("colHeaders", colHeaders);
        json.put("rowHeaders", rowHeaders);
        json.put("data", data);
        
        CSVParser parser = new CSVParser();
        BufferedReader text = new BufferedReader(new StringReader(csvString));
		
		try{
			String line = text.readLine();
			while((line = text.readLine()) != null){
				String[] dataArray = parser.parseLine(line);
				rowHeaders.add(dataArray[0]);
				JSONArray rows = new JSONArray();
				for(int i = 1; i < dataArray.length; i++){
					rows.add(new Long(dataArray[i]));
					
				}
				
				//rows.add(new Long(data[1]));
				//rows.add(new Long(data[2]));
				//rows.add(new Long(data[3]));
				//rows.add(new Long(data[4]));
				data.add(rows);
			}
		}catch (IOException e) {e.printStackTrace();}
		
		return json.toString();
    }
    
    public static String jsonToCsv(String jsonString) {
       JSONObject json = null;

        try {
            JSONParser detail = new JSONParser();
            json = (JSONObject) detail.parse(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		JSONArray col = (JSONArray) json.get("colHeaders");
		String csv = "\"ID\"," + Converter.jsonCompactor(col) + "\n";
		JSONArray row = (JSONArray) json.get("rowHeaders");
		JSONArray fileData = (JSONArray) json.get("data");
		
		
		
		for(int i = 0; i < row.size(); i++){
			csv += "\"" + (String)row.get(i) + "\"" + "," + Converter.jsonCompactor((JSONArray)fileData.get(i)) + "\n";
		}
		return csv;
    }
    
    	public static <T> String jsonCompactor(JSONArray arr){
		String csv = "";
		for(int i =0; i < arr.size(); i++){
			csv += "\"" + arr.get(i) + "\""; 
			if(i < arr.size() - 1){
				csv += ",";
			}
		}
		return csv;
	}
}













