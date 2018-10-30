//Unified API - TestUtil.java
package com.qa.util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
public class TestUtil {
// This method parse the JSON and return string of data
	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
		
	} // method
	
//Generate a random number	
	public static int get_randomNumber(int upperLimit) {
    	Random objGenerator = new Random();
          int randomNumber = objGenerator.nextInt(upperLimit);
          //System.out.println("Random No : " + randomNumber);
          return randomNumber;
	}	//Random number method
	
	
//Read data from JSON in the form of string
	public static String get_JsonAsString(String strJsonFileName) {
	    // strJsonFileName -- name of the JSOn file with extension
		String JSONString = "";
		try {
        	JSONString = new String(Files.readAllBytes( Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/"+strJsonFileName+".json")));
        	} 
		catch (IOException e) {
          e.printStackTrace();
        	}
		return JSONString;
	} // Method get_JsonAsString
	
// generate a random lower case string for a given length
	public static String getRandomString(int stringLength) {		  
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = stringLength;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	 
	    return generatedString;
	} //Method getRandomString
	

	
} // class