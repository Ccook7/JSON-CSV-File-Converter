package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    private String csvString;
    private String jsonString;

    @Before
    public void setUp() {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
        StringBuffer csvContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.csv")))) {
            String line;
            while((line = reader.readLine()) != null) {
                csvContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        csvString = csvContents.toString();
		
        
        StringBuffer jsonContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.json")))) {
            String line;
            while((line = reader.readLine()) != null) {
                jsonContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        jsonString = jsonContents.toString();
		jsonString = Converter.csvToJson(csvString);
		
    }
    
    @Test
    public void testConvertCSVtoJSON() {
        assertEquals(jsonString, Converter.csvToJson(csvString));
    }

    @Test
    public void testConvertJSONtoCSV() {
        assertEquals(csvString, Converter.jsonToCsv(jsonString));
    }
}







