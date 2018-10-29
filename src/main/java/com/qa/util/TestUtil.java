//Unified API - TestUtil.java
package com.qa.util;
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

} // class