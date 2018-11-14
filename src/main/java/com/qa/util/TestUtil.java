//Unified API - TestUtil.java
package com.qa.util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult ;


public class TestUtil {


	//ExtentReport class object	
	 static ExtentReports extent_report;
	 static ExtentTest extent_test;

	 
// To parse the JSON and return string of data
	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
		
	} // method getValueByPath
	
	
//Generate a random number	
	public static int get_randomNumber(int upperLimit) {
    	Random objGenerator = new Random();
          int randomNumber = objGenerator.nextInt(upperLimit);
          //System.out.println("Random No : " + randomNumber);
          return randomNumber;
	}	//Random number method
	
	
//Read data from JSON in the form of string
	public static String get_JsonAsString(String strJsonFileName) {
	    // strJsonFileName -- name of the JSON file with extension
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
	

// write result in Console and ExtentReport with Expected and Actual
	public static void writeResult(String strResultType, String strOutputTitle,  String strExpectedOutput, String strActualOutput) {

			//write to console 
			System.out.println(strOutputTitle+"> Expected:"+strExpectedOutput+", Actual:"+strActualOutput);
			//write to extentReport 
			if (strResultType == "INFO" ){
				 extent_test.log(LogStatus.INFO, strOutputTitle+"> Expected:"+strExpectedOutput+ ", Actual:"+strActualOutput);
			 }
		     else if (strResultType == "PASS" )  {
		    	 extent_test.log(LogStatus.PASS, strOutputTitle+"> Expected:"+strExpectedOutput+ ", Actual:"+strActualOutput); 
		     }
		     else if(strResultType == "FAIL" ) {
		    	 extent_test.log(LogStatus.FAIL, strOutputTitle+"> Expected:"+strExpectedOutput+ ", Actual:"+strActualOutput); 
		     }
		     else if(strResultType == "WARNING" ) {
		    	 extent_test.log(LogStatus.WARNING, strOutputTitle+"> Expected:"+strExpectedOutput+ ", Actual:"+strActualOutput); 
		     }	 
		} // Method WriteResult

	
//write result in Console and ExtentReport without any expected value
	public static void writeResult(String strResultType, String strOutputTitle, String strActualOutput) {

			//write to console 
			System.out.println(strOutputTitle+": "+strActualOutput);
			//write to extentReport 
			if (strResultType == "INFO" ){
				 extent_test.log(LogStatus.INFO, strOutputTitle+": "+ strActualOutput);
			 }
		     else if (strResultType == "PASS" )  {
		    	 extent_test.log(LogStatus.PASS, strOutputTitle+": "+ strActualOutput); 
		     }
		     else if(strResultType == "FAIL" ) {
		    	 extent_test.log(LogStatus.FAIL, strOutputTitle+": "+ strActualOutput); 
		     }
		     else if(strResultType == "WARNING" ) {
		    	 extent_test.log(LogStatus.WARNING, strOutputTitle+": "+ strActualOutput); 
		     }	 
		} // Method WriteResult	

	
//print Summary in extent-report	
	public static void getResult(int intTestStatus, String strTestName){
		if(intTestStatus == ITestResult.FAILURE){
			extent_test.log(LogStatus.FAIL, "Summary --- '"+strTestName+"' has failed");
		}
		else if(intTestStatus == ITestResult.SKIP){
			extent_test.log(LogStatus.SKIP, "Summary --- '"+strTestName+"' is skipped");
		}
		else if(intTestStatus == ITestResult.SUCCESS){
			extent_test.log(LogStatus.PASS, "Summary --- '"+strTestName+"' has passed");
		}
	}// Method getResult
	

// start-test method of Extent report
	public static void startTest()
	{
		extent_report = new ExtentReports(System.getProperty("user.dir")+"\\ExecutionResults.html", true);
		extent_report.addSystemInfo("Suite Description", "API calls to MDM system");
		extent_report.loadConfig(new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\extentReport\\extent-config.xml"));
	}

	
// start-test method of Extent report
	public static void startTest(String strTestName)
	{	
		extent_test = extent_report.startTest(strTestName);
	}
	

// end-test method of Extent report
	public static void endTest()
	{
	//endTest 	
		extent_report.endTest(extent_test);
		extent_report.flush();	
	}

	
} // class