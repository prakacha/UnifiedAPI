//Unified API - PostAPICreateNewCustomer.java
package com.qa.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class PostAPI_CreateNewCustomer extends TestBase {	
	TestBase testBase;
	String serviceUrl_CreateCustomer;
	String serviceUrl_SearchCustomer;
	String apiUrl_CreateCustomer;
	String url_MasterAPI;
	String url_Create;
	String url_Search;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
@BeforeMethod
	public void setup() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		url_MasterAPI = prop.getProperty("MasterAPI_URL");
		serviceUrl_CreateCustomer = prop.getProperty("serviceURL_CreateCustomer");
		serviceUrl_SearchCustomer = prop.getProperty("serviceURL_SearchCustomer");
		url_Create = url_MasterAPI + serviceUrl_CreateCustomer;
		url_Search = url_MasterAPI + serviceUrl_SearchCustomer;
	}	//Method SetUp close
	
@Test(priority =0)
	public void postAPITest_Create() throws ClientProtocolException, IOException {
		System.out.println("POST API to create customer------------------------------------------------------------");
		System.out.flush();	
		
//Create a hash map for passing header		
			HashMap<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
//			headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
//			headerMap.put("Password", "Password_Value");		// pass value like password only if required
					
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
        System.out.println("Agreement type: "+strAgreementtype);
        
//retrieve two level element
        System.out.println("Response with Initial firstName " + responseJson);
        JSONObject responseJson_basicCustomerInfo = responseJson.getJSONObject("basicCustomerInfo");
        responseJson_basicCustomerInfo.remove("firstName");
        System.out.println("Response without firstName " + responseJson);
        responseJson_basicCustomerInfo.put("firstName", "test_97");
        System.out.println("Response with final firstName " + responseJson);
        
//Convert JSONObject back into String        
        usersJSONString = responseJson.toString();

// update JSON file with new details        
        try {
            Files.write(Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_CreateCustomer.json"), usersJSONString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
       
//POST the request
		restClient = new RestClient();	
		closeableHttpResponse = restClient.post(url_Create, usersJSONString, headerMap);		// hit the API	
//Validate Status Code
		int strStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code: "+strStatusCode);
		Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);		
//retrieve response string 			
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
		System.out.println("Response String from API: " + responseString);
		
//Validate the Response
		//Pia to update more on this
					
	} // Method postAPITest_Create 


@Test(priority =1)
public void postAPITest_Search() throws ClientProtocolException, IOException {
		System.out.println("");
		System.out.println("");
		System.out.println("POST API to search customer------------------------------------------------------------");
		System.out.flush();	
//Create a hash map for passing header		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");	// 'content header' as application/JSON
//		headerMap.put("userName", "UserName_Value");		// pass value like user-name only if required
//		headerMap.put("Password", "Password_Value");		// pass value like password only if required
				
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
		closeableHttpResponse = restClient.post(url_Search, usersJSONString, headerMap);		// hit the API

//Validate Status Code
		int strStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code: "+strStatusCode);
		Assert.assertEquals(strStatusCode, RESPONSE_STATUS_CODE_200);		
//retrieve response string 			
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8" );	//return the response in string format
		System.out.println("Response String from API: " + responseString);	
		
	} // Method postAPITest_Search

} //Class PostAPI_CreateNewCustomer

