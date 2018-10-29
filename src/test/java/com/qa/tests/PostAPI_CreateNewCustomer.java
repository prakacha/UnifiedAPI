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
		extent_report = new ExtentReports(System.getProperty("user.dir")+"\\ExecutionResults.html", false);
		extent_report.addSystemInfo("Suite Description", "API calls to MDM system");
		System.out.println(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\extentReport\\extent-config.xml");
		extent_report.loadConfig(new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\extentReport\\extent-config.xml"));

		
	}
	
// POST API call to create customer
@Test
	public void test_Post_API_Create() throws ClientProtocolException, IOException {
			System.out.println("TEST#1 POST API to create customer------------------------------------------------------------");
			System.out.flush();
			
			extent_test = extent_report.startTest("test_Post_API_Create");
			extent_test.log(LogStatus.INFO, "Test Description: create customer using post API");
			
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required
						
		// Read data from JSON in the form of string 
			String usersJSONString = "";
	        try {
	        	usersJSONString = new String(Files.readAllBytes( Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_CreateCustomer.json")));
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	        
	    //Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	
	    //retrieve one level element from JSON
	        Object strAgreementtype = responseJson.get("agreementType");
	        //System.out.println("Agreement type: "+strAgreementtype);
	        extent_test.log(LogStatus.INFO, "AgreementType: "+strAgreementtype);
	        
	    //retrieve two level element
	        JSONObject responseJson_basicCustomerInfo = responseJson.getJSONObject("basicCustomerInfo");
	        responseJson_basicCustomerInfo.remove("firstName");
	   //get a random number for first name
			int randomNumber_firstName = TestUtil.get_randomNumber(1000);
	        responseJson_basicCustomerInfo.put("firstName", "test_"+randomNumber_firstName);
	        System.out.println("firstName: " + "test_"+randomNumber_firstName);
	        extent_test.log(LogStatus.INFO, "First Name: "+ "test_"+randomNumber_firstName);
	        //System.out.println("Response with randomly generated firstName " + responseJson);
	    //get a random number for last name     
	        int randomNumber_LastName = TestUtil.get_randomNumber(1000);
	        responseJson_basicCustomerInfo.remove("lastName");
	        responseJson_basicCustomerInfo.put("lastName", "test_"+randomNumber_LastName);
	        System.out.println("lastName: " + "test_"+randomNumber_LastName);
	        extent_test.log(LogStatus.INFO, "Last Name: "+ "test_"+randomNumber_LastName);
	        
	     //retrieve two level array-element  
	        JSONObject responseJson_CustomerContactInfo = responseJson.getJSONObject("customerContactInfo");
	        JSONArray responseJson_Array_Contacts = responseJson_CustomerContactInfo.getJSONArray("contacts");
	        //System.out.println("Contacts Array item --- >"+responseJson_Array_Contacts.get(0));
	        JSONObject responseJson_Contacts = responseJson_Array_Contacts.getJSONObject(0);
	        JSONObject responseJson_Contact = responseJson_Contacts.getJSONObject("contact");
	       //System.out.println("e-Mail: "+responseJson_Contact.get("value"));	//retrieve email address
	    //get a random number for email-
	        int randomNumber_email = TestUtil.get_randomNumber(1000);
	        responseJson_Contact.remove("value");
	        responseJson_Contact.put("value", "Test_"+randomNumber_email+"@test.com");
	        System.out.println("e-Mail: "+"Test_"+randomNumber_email+"@test.com");	//retrieve email address
	        extent_test.log(LogStatus.INFO, "eMail: "+ "test_"+randomNumber_email+"@test.com");
	        
	    //Convert JSONObject back into String        
	        usersJSONString = responseJson.toString();
	        System.out.println("Request String: " + usersJSONString);
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
			System.out.println("Status code: "+strStatusCode);
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
			
		//Write result in Extent report			
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				extent_test.log(LogStatus.PASS, "Response Code: "+strStatusCode);
			}
			else {
				extent_test.log(LogStatus.FAIL, "Response Code: "+strStatusCode);
			}
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			System.out.println("Response String: " + responseString);
			extent_test.log(LogStatus.INFO, "Response String: "+ responseString);
			
		//Validate the Response
			//Pia/Hannu to update more on this
			
		//endTest 	
			extent_report.endTest(extent_test);
			extent_report.flush();		
							
	} // Method postAPITest_Create 


//POST API call to search customer
@Test
	public void test_Post_API_Search() throws ClientProtocolException, IOException {
			System.out.println("");
			System.out.println("");
			System.out.println("TEST#2 POST API to search customer------------------------------------------------------------");
			System.out.flush();
			extent_test = extent_report.startTest("test_Post_API_Search");
			extent_test.log(LogStatus.INFO, "Test description: search customer using post API");
			
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required
					
		//Read data from JSON in the form of string 
			String usersJSONString = "";
		    try {
		    	usersJSONString = new String(Files.readAllBytes( Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_SearchCustomer.json")));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }	
	
		//Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);	    
		    
		    
	    //POST the request
			restClient = new RestClient();	
			closeableHttpResponse = restClient.post(url_Search, usersJSONString, headerMap);		// hit API
	
		//Validate Status Code
			int strStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
			System.out.println("Status code: "+strStatusCode);
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
	
		//Write result in Extent report		
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				extent_test.log(LogStatus.PASS, "Response Code: "+strStatusCode);
			}
			else {
				extent_test.log(LogStatus.FAIL, "Response Code: "+strStatusCode);
			}
			
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			System.out.println("Response String from API: " + responseString);	
		  //extent_test.log(LogStatus.FAIL, "Response String: "+responseString);
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

} //Class PostAPI_CreateNewCustomer

