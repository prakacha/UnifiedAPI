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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
import com.qa.util.ReadEmails;
import com.qa.util.UpdateJSON;

@Listeners(com.qa.listener.Test_Listener.class)

public class PostAPI_CreateNewCustomer extends TestBase {
	
// declaration of class objects	
	//user defined Class object
	TestBase testBase;
	UpdateJSON updatejson;
	String serviceUrl_CreateCustomer;
	String serviceUrl_SearchCustomer;
	String apiUrl_CreateCustomer;
	String url_MasterAPI;
	String url_Create;
	String url_Search;
	String url_verifyEmail;
	String strMailFrom;
	String randomString_firstName;
	String randomString_LastName;
	String randomString_eMail;
	String authorization_username;
	String authorization_password;
	String authorization_Value;
	String generate_Mail_Id;
	String generate_FirstName;
	String generate_LastName;
	String mail_userName;
	RestClient restClient;
	


	
// ExtentReport- startTest Method
@BeforeTest
	public static void custom_startTest() {
		TestUtil.startTest();	
	}	
	
// build API url and Authorization
@BeforeMethod
	public void setup() throws ClientProtocolException, IOException{
		testBase = new TestBase();
	//uri
		url_MasterAPI = prop.getProperty("MasterAPI_URL");
		serviceUrl_CreateCustomer = prop.getProperty("serviceURL_CreateCustomer");
		serviceUrl_SearchCustomer = prop.getProperty("serviceURL_SearchCustomer");
		url_Create = url_MasterAPI + serviceUrl_CreateCustomer;
		url_Search = url_MasterAPI + serviceUrl_SearchCustomer;	
	// Header Authorization
		authorization_username = prop.getProperty("username");
		authorization_password = prop.getProperty("password");
		authorization_Value = "Basic "+Base64.getEncoder().encodeToString((authorization_username+":"+authorization_password).getBytes("utf-8"));
		mail_userName = prop.getProperty("mail_username");	// get customer mail ID
	//Automated data generation
		generate_Mail_Id = prop.getProperty("generate_Random_Mail_Id");
		generate_FirstName = prop.getProperty("generate_Random_FirstName");
		generate_LastName = prop.getProperty("generate_Random_LastName");
	}	//Method SetUp 
	
// POST API call to create customer
@Test(priority = 0, invocationCount = 1, enabled = true)
	public void test_Post_API_Create() throws ClientProtocolException, IOException {
			CloseableHttpResponse closeableHttpResponse_Create;
			
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
			//headerMap.put("userName", authorization_username);		// pass value like user-name only if required
			//headerMap.put("Password", authorization_password);		// pass value like password only if required					
						
		// Read data from JSON in the form of string 
			String usersJSONString = "";
			usersJSONString = TestUtil.get_JsonAsString("data_CreateCustomer");
	        
	    //Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	
	    //retrieve one level element from JSON
	        Object strAgreementtype = responseJson.get("agreementType");
	        TestUtil.writeResult ("INFO", "AgreementType", strAgreementtype.toString());		//write result
	             
	    //retrieve two level element
	        JSONObject responseJson_basicCustomerInfo = responseJson.getJSONObject("basicCustomerInfo");	        
	        
	   //get a random string for first name
	        if (Boolean.parseBoolean(generate_FirstName.toLowerCase().trim())==true) {
	        	responseJson_basicCustomerInfo.remove("firstName");
				randomString_firstName = "test_"+TestUtil.getRandomString(5);
		        responseJson_basicCustomerInfo.put("firstName", randomString_firstName);    
	        }
	        else {
	        	randomString_firstName = responseJson_basicCustomerInfo.get("firstName").toString();
	        }
	        	TestUtil.writeResult ("INFO", "First Name", randomString_firstName);		//write result
	        //System.out.println("Response with randomly generated firstName " + responseJson);
	        
	        
	    //get a random string for last name
	        if (Boolean.parseBoolean(generate_LastName.toLowerCase().trim())==true) {
		        randomString_LastName = "test_"+ TestUtil.getRandomString(5);
		        responseJson_basicCustomerInfo.remove("lastName");
		        responseJson_basicCustomerInfo.put("lastName", randomString_LastName);		        
	        }
	        else {
	        	randomString_LastName = responseJson_basicCustomerInfo.get("lastName").toString();
	        }
	        	TestUtil.writeResult ("INFO", "Last Name", randomString_LastName);	//write result	        
	        
	     //retrieve two level array-element    
		        JSONObject responseJson_CustomerContactInfo = responseJson.getJSONObject("customerContactInfo");
		        JSONArray responseJson_Array_Contacts = responseJson_CustomerContactInfo.getJSONArray("contacts");
		        //System.out.println("Contacts Array item --- >"+responseJson_Array_Contacts.get(0));
		        JSONObject responseJson_Contacts = responseJson_Array_Contacts.getJSONObject(0);
		        JSONObject responseJson_Contact = responseJson_Contacts.getJSONObject("contact");
		        //System.out.println("e-Mail: "+responseJson_Contact.get("value"));	//retrieve email address
		 	   // get a random string for mail id 		        
		   if (Boolean.parseBoolean(generate_Mail_Id.toLowerCase().trim())==true) {
			   	String[] arr_mailUserName = mail_userName.split("@");
		        int randomNumber_email = TestUtil.get_randomNumber(9999999);	
		        randomString_eMail = arr_mailUserName[0]+"+"+randomNumber_email+"@"+arr_mailUserName[1];	//get a random value for email
		        // note: if your id is auto@gmail.com, you could send mail to auto+mono@gmail.com or auto+dual@gmail.com.
		        if (responseJson.has("emailId")) {		//as this emailID was not there in Main JSON
		        	responseJson.put("emailId", randomString_eMail);
		        }
		        responseJson_Contact.remove("value");
		        responseJson_Contact.put("value", randomString_eMail);		        
	        }
		   
		   else {
			   	randomString_eMail = responseJson_Contact.get("value").toString(); 
		   	}
		   		TestUtil.writeResult ("INFO", "eMail", randomString_eMail);		//write result
		        
		        
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
			closeableHttpResponse_Create = restClient.post(url_Create, usersJSONString, headerMap);		// hit API
			
		//Validate Status Code
			int strStatusCode = closeableHttpResponse_Create.getStatusLine().getStatusCode();
					
		//Write result 		
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse_Create.getEntity(),"UTF-8" );	//return the response in string format
			TestUtil.writeResult ("INFO", "Response String", responseString);
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
			
		//Validate the Response
			//
			
		//end test
			TestUtil.endTest();	
	} // Method postAPITest_Create 



//POST API call to verify email
@Test(priority = 1, invocationCount = 1, enabled = true)
	public void test_Get_API_VerifyEmail() throws ClientProtocolException, IOException, InterruptedException {	
		CloseableHttpResponse closeableHttpResponse_VerifyMail;
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("TEST#2: verify email");
		System.out.println("-------------------------------------");
		TestUtil.startTest("test_Get_API_VerifyEmail");
		strMailFrom = prop.getProperty("mail_from");
	// fetch email content 
		ReadEmails reademail = new ReadEmails();
		String strMailContent = reademail.readMails(randomString_firstName, strMailFrom, randomString_eMail);
		//System.out.println(strMailContent);
		if (strMailContent.contains("true")) {
	//using JSOUP library to parse the verification link	
			Document doc = Jsoup.parse(strMailContent);
			Element link = doc.select("a").first();
			//String mailText = doc.body().text();	// email body 
			url_verifyEmail = link.attr("href"); // retrieves verification link with token
			//String linkText = link.text(); 	// text attached with the hyper-link
			TestUtil.writeResult ("INFO", "API", url_verifyEmail.toString());
		//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
			//headerMap.put("Authorization", authorization_Value);	// user-name and password with base64 encryption separated by colon
			//headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
			//headerMap.put("Password", "Password_Value");		// pass value like password only if required
			
		//GET the request
			restClient = new RestClient();	
			closeableHttpResponse_VerifyMail = restClient.get(url_verifyEmail, headerMap);		// hit API to verify mail
			
			
		//Validate Status Code
			int strStatusCode = closeableHttpResponse_VerifyMail.getStatusLine().getStatusCode();
			

		//Write result 	
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse_VerifyMail.getEntity(),"UTF-8" );	//return the response in string format
			TestUtil.writeResult ("INFO", "Response String", responseString);
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);	
		} //if
		
		else {
			TestUtil.writeResult ("FAIL", "Error", strMailContent );
			Assert.fail("Mail not found in mailbox.");	
			} //else
		
	//end test
		TestUtil.endTest();	
		
}	// Method test_Get_API_VerifyEmail



//POST API call to search customer
@Test(priority = 2, invocationCount = 1, enabled = true)
	public void test_Post_API_Search() throws ClientProtocolException, IOException, InterruptedException {
			CloseableHttpResponse closeableHttpResponse_Search;
			System.out.println("");
			System.out.println("");
			System.out.println("-------------------------------------");
			System.out.println("TEST#3: Search Customer");
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
			closeableHttpResponse_Search = restClient.post(url_Search, usersJSONString, headerMap);		// hit API
	
		//Validate Status Code
			int strStatusCode = closeableHttpResponse_Search.getStatusLine().getStatusCode();
	
		//Write result 	
			if (strStatusCode == RESPONSE_STATUS_CODE_200){
				TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			else {
				TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
			}
			
		//retrieve response string 			
			String responseString = EntityUtils.toString(closeableHttpResponse_Search.getEntity(),"UTF-8" );	//return the response in string format
			TestUtil.writeResult ("INFO", "Response String", responseString);
			Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);
			
		//end test
			TestUtil.endTest();	
			
	} // Method postAPITest_Search



//PUT API call to update customer information // updates customer consent, agreement and contact information
@Test(priority = 3, invocationCount = 1, enabled = true)
	public void test_Put_API_UpdateCustomerInfo() throws ClientProtocolException, IOException, InterruptedException {	
		CloseableHttpResponse closeableHttpResponse_UpdateCustInfo;
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("TEST#4: Update Customer Information");
		System.out.println("-------------------------------------");
		TestUtil.startTest("test_Put_API_UpdateCustomerInfo");
		TestUtil.writeResult ("INFO", "API", url_Create.toString());
	//Create a hash map for passing header		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
		headerMap.put("Authorization", authorization_Value);	// user-name and password with base64 encryption separated by colon

	// Edit the JSON data and get the updated data
		String usersJSONString = "";
		updatejson = new UpdateJSON();
		usersJSONString = updatejson.updateCustomerDetails();	//update the CreateCustomer JSON 
		
		
	//POST the request
		restClient = new RestClient();	
		closeableHttpResponse_UpdateCustInfo = restClient.put(url_Create, usersJSONString, headerMap);		// hit API

	//Validate Status Code
		int strStatusCode = closeableHttpResponse_UpdateCustInfo.getStatusLine().getStatusCode();

	//Write result 	
		if (strStatusCode == RESPONSE_STATUS_CODE_200){
			TestUtil.writeResult("PASS", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
		}
		else {
			TestUtil.writeResult("FAIL", "Response Code", String.valueOf(RESPONSE_STATUS_CODE_200) , Integer.toString(strStatusCode));
		}	
		
	//retrieve response string 			
		String responseString = EntityUtils.toString(closeableHttpResponse_UpdateCustInfo.getEntity(),"UTF-8" );	//return the response in string format
		TestUtil.writeResult ("INFO", "Response String", responseString);
		Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);	
			
	//end test
			TestUtil.endTest();	
				
		}	// Method test_Get_API_VerifyEmail


//ExtentReport- endTest Method
@AfterMethod
	public void custom_getResult() {

	}


//ExtentReport- endTest Method
@AfterTest
	public static void custom_endTest() {
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("Summary:");
		System.out.println("-------------------------------------");
		TestUtil.endTest();
	}
		

} //Class PostAPI_CreateNewCustomer

