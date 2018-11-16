//Unified API - PostAPICreateNewCustomer.java
package com.qa.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

@Listeners(com.qa.listener.Test_Listener.class)

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
	String authorization_username;
	String authorization_password;
	String authorization_Value;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	
// ExtentReport- startTest Method
@BeforeTest
	public static void custom_startTest() {
		TestUtil.startTest();	
	}	
	
// build API url	
@BeforeMethod
	public void setup() throws ClientProtocolException, IOException{
		testBase = new TestBase();
	//uri
		url_MasterAPI = prop.getProperty("MasterAPI_URL");
		serviceUrl_CreateCustomer = prop.getProperty("serviceURL_CreateCustomer");
		serviceUrl_SearchCustomer = prop.getProperty("serviceURL_SearchCustomer");
		url_Create = url_MasterAPI + serviceUrl_CreateCustomer;
		url_Search = url_MasterAPI + serviceUrl_SearchCustomer;	
	// Authorization
		authorization_username = prop.getProperty("username");
		authorization_password = prop.getProperty("password");
		authorization_Value = Base64.getEncoder().encodeToString((authorization_username+":"+authorization_password).getBytes("utf-8"));
	}	//Method SetUp 
	
// POST API call to create customer
@Test(priority = 0, invocationCount = 1, enabled = true)
	public void test_Post_API_Create() throws ClientProtocolException, IOException {
	
			System.out.println("");
			System.out.println("-------------------------------------");
			System.out.println("TEST#1: Create Customer");
			System.out.println("-------------------------------------");
			
		//Start test	
			TestUtil.startTest("test_Post_API_Create");
			TestUtil.writeResult ("INFO", "API", url_Create.toString());
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			headerMap.put("Authorization", authorization_Value);	// user-name and password with base64 encryption separated by colon
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required					
						
		// Read data from JSON in the form of string 
			String usersJSONString = "";
			usersJSONString = TestUtil.get_JsonAsString("data_CreateCustomer");
	        
	    //Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	
	    //retrieve one level element from JSON
	        Object strAgreementtype = responseJson.get("agreementType");
	        TestUtil.writeResult ("INFO", "AgreementType", strAgreementtype.toString());
	        
	    //retrieve two level element
	        JSONObject responseJson_basicCustomerInfo = responseJson.getJSONObject("basicCustomerInfo");
	        responseJson_basicCustomerInfo.remove("firstName");
	        
	   //get a random string for first name
			randomString_firstName = "test_"+TestUtil.getRandomString(5);
	        responseJson_basicCustomerInfo.put("firstName", randomString_firstName);
	        TestUtil.writeResult ("INFO", "First Name", randomString_firstName);
	        //System.out.println("Response with randomly generated firstName " + responseJson);
	        
	    //get a random string for last name     
	        randomString_LastName = "test_"+ TestUtil.getRandomString(5);
	        responseJson_basicCustomerInfo.remove("lastName");
	        responseJson_basicCustomerInfo.put("lastName", randomString_LastName);
	        TestUtil.writeResult ("INFO", "Last Name", randomString_LastName);
	        
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
	        TestUtil.writeResult ("INFO", "eMail", randomString_eMail);
	        
	    //Convert JSONObject back into String        
	        usersJSONString = responseJson.toString();
	        TestUtil.writeResult ("INFO", "Request String", usersJSONString);
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
				TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			TestUtil.writeResult ("INFO", "Response String", responseString);
			
		//Validate the Response
			//Pia/Hannu to update more on this
			
		//end test
			TestUtil.endTest();	
	} // Method postAPITest_Create 


//POST API call to search customer
@Test(priority = 1, invocationCount = 1, enabled = true)
	public void test_Post_API_Search() throws ClientProtocolException, IOException {
			System.out.println("");
			System.out.println("");
			System.out.println("-------------------------------------");
			System.out.println("TEST#2: Search Customer");
			System.out.println("-------------------------------------");
			
			TestUtil.startTest("test_Post_API_Search");
			TestUtil.writeResult ("INFO", "API", url_Search.toString());
			
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			headerMap.put("Authorization", authorization_Value);	// user-name and password with base64 encryption separated by colon
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
	        TestUtil.writeResult ("INFO", "Request String", usersJSONString);
	        
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
				TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
			TestUtil.writeResult ("INFO", "Response String", responseString);
			
		//end test
			TestUtil.endTest();	
			
	} // Method postAPITest_Search



//ExtentReport- endTest Method
@AfterMethod
	public void custom_getResult() {

	}


//ExtentReport- endTest Method
@AfterTest
	public static void custom_endTest() {
		TestUtil.endTest();
	}
		

} //Class PostAPI_CreateNewCustomer

