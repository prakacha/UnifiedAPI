//Unified API - PostAPICreateNewCustomer.java
package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.qa.util.TestUtil;

public class PostAPI_CreateNewCustomer extends TestBase {
	
// declaration of class objects	
	//user defined Class object
	TestBase testBase;
	String serviceUrl_CreateCustomer;
	String serviceUrl_SearchCustomer;
	String apiUrl_CreateCustomer;
	String url_MasterAPI;
	String url_Create;
	String url_Search;
	String randomString_firstName;
	String randomString_LastName;
	String randomString_eMail;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	
	//ExtentReport class object	
	static ExtentReports extent_report;
	static ExtentTest extent_test;

// build API url	
@BeforeMethod
	public void setup() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		url_MasterAPI = prop.getProperty("MasterAPI_URL");
		serviceUrl_CreateCustomer = prop.getProperty("serviceURL_CreateCustomer");
		serviceUrl_SearchCustomer = prop.getProperty("serviceURL_SearchCustomer");
		url_Create = url_MasterAPI + serviceUrl_CreateCustomer;
		url_Search = url_MasterAPI + serviceUrl_SearchCustomer;
	}	//Method SetUp close

// ExtentReport- startTest Method
@BeforeTest
	public static void startTest()
	{
		extent_report = new ExtentReports(System.getProperty("user.dir")+"\\ExecutionResults.html", true);
		extent_report.addSystemInfo("Suite Description", "API calls to MDM system");
		extent_report.loadConfig(new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\extentReport\\extent-config.xml"));	
	}
	
// POST API call to create customer
@Test(priority = 0)
	public void test_Post_API_Create() throws ClientProtocolException, IOException {
			System.out.println("");
			System.out.println("-------------------------------------");
			System.out.println("TEST#1: Create Customer");
			System.out.println("-------------------------------------");
			
			extent_test = extent_report.startTest("test_Post_API_Create");
			
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required					
						
		// Read data from JSON in the form of string 
			String usersJSONString = "";
			usersJSONString = TestUtil.get_JsonAsString("data_CreateCustomer");
	        
	    //Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	
	    //retrieve one level element from JSON
	        Object strAgreementtype = responseJson.get("agreementType");
	        writeResult ("INFO", "AgreementType", strAgreementtype.toString());
	        
	    //retrieve two level element
	        JSONObject responseJson_basicCustomerInfo = responseJson.getJSONObject("basicCustomerInfo");
	        responseJson_basicCustomerInfo.remove("firstName");
	        
	   //get a random string for first name
			randomString_firstName = "test_"+TestUtil.getRandomString(5);
	        responseJson_basicCustomerInfo.put("firstName", randomString_firstName);
	        writeResult ("INFO", "First Name", randomString_firstName);
	        //System.out.println("Response with randomly generated firstName " + responseJson);
	        
	    //get a random string for last name     
	        randomString_LastName = "test_"+ TestUtil.getRandomString(5);
	        responseJson_basicCustomerInfo.remove("lastName");
	        responseJson_basicCustomerInfo.put("lastName", randomString_LastName);
	        writeResult ("INFO", "Last Name", randomString_LastName);
	        
	     //retrieve two level array-element  
	        JSONObject responseJson_CustomerContactInfo = responseJson.getJSONObject("customerContactInfo");
	        JSONArray responseJson_Array_Contacts = responseJson_CustomerContactInfo.getJSONArray("contacts");
	        //System.out.println("Contacts Array item --- >"+responseJson_Array_Contacts.get(0));
	        JSONObject responseJson_Contacts = responseJson_Array_Contacts.getJSONObject(0);
	        JSONObject responseJson_Contact = responseJson_Contacts.getJSONObject("contact");
	       //System.out.println("e-Mail: "+responseJson_Contact.get("value"));	//retrieve email address
	        int randomNumber_email = TestUtil.get_randomNumber(9999999);	
	        randomString_eMail = "Test_"+randomNumber_email+"@test.com";	//get a random value for email-
	        responseJson_Contact.remove("value");
	        responseJson_Contact.put("value", randomString_eMail);
	        writeResult ("INFO", "eMail", randomString_eMail);
	        
	    //Convert JSONObject back into String        
	        usersJSONString = responseJson.toString();
	        writeResult ("INFO", "Request String", usersJSONString);
	    // update JSON file with new details        
	        try {
	            Files.write(Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_CreateCustomer.json"), usersJSONString.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       
	    //POST the request
			restClient = new RestClient();	
			closeableHttpResponse = restClient.post(url_Create, usersJSONString, headerMap);		// hit API
			
		//Validate Status Code
			int strStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
							
		//Write result 		
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			writeResult ("INFO", "Response String", responseString);
			
		//Validate the Response
			//Pia/Hannu to update more on this
			
		//endTest 	
			extent_report.endTest(extent_test);
			extent_report.flush();	
							
	} // Method postAPITest_Create 


//POST API call to search customer
@Test(priority = 0)
	public void test_Post_API_Search() throws ClientProtocolException, IOException {
			System.out.println("");
			System.out.println("");
			System.out.println("-------------------------------------");
			System.out.println("TEST#2: Search Customer");
			System.out.println("-------------------------------------");
			
			extent_test = extent_report.startTest("test_Post_API_Search");
			extent_test.log(LogStatus.INFO, "Test description: search customer using post API");
			
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required
					
		//Read data from JSON in the form of string 				
			String usersJSONString = "";
			usersJSONString = TestUtil.get_JsonAsString("data_SearchCustomer");
	
		//Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	    // update JSON     
	        responseJson.put("firstName", randomString_firstName);
	        responseJson.put("lastName", randomString_LastName);
//	        responseJson.put("emailId", randomString_eMail);		// not able to search customer with email
	        
	    //Convert JSONObject back into String        
	        usersJSONString = responseJson.toString();
	        writeResult ("INFO", "Request String", usersJSONString);
	        
	    // update JSON file with new details        
	        try {
	            Files.write(Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_SearchCustomer.json"), usersJSONString.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }    
	        
	    //POST the request
			restClient = new RestClient();	
			closeableHttpResponse = restClient.post(url_Search, usersJSONString, headerMap);		// hit API
	
		//Validate Status Code
			int strStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
	
		//Write result 	
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			writeResult ("INFO", "Response String", responseString);
		//endTest 
			extent_report.endTest(extent_test);
			extent_report.flush();	
				
	} // Method postAPITest_Search



//ExtentReport- getResult Method
@AfterMethod
	public void getResult(ITestResult result){
			if(result.getStatus() == ITestResult.FAILURE){
				extent_test.log(LogStatus.FAIL, "Summary --- '"+result.getName()+"' has failed");
				extent_test.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
			}
			else if(result.getStatus() == ITestResult.SKIP){
				extent_test.log(LogStatus.SKIP, "Summary --- '"+result.getName()+"' is skipped");
			}
			else if(result.getStatus() == ITestResult.SUCCESS){
				extent_test.log(LogStatus.PASS, "Summary --- '"+result.getName()+"' has passed");
			}
	}

//ExtentReport- endTest Method
@AfterTest
	public void endTest() {
		extent_report.endTest(extent_test);
		extent_report.flush();
	}

public void writeResult(String strResultType, String strOutputTitle,  String strExpectedOutput, String strActualOutput) {
// write result in Console and ExtentReport with Expected and Actual
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

public void writeResult(String strResultType, String strOutputTitle, String strActualOutput) {
// write result in Console and ExtentReport without any expected value
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

} //Class PostAPI_CreateNewCustomer

