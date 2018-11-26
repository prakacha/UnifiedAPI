package com.qa.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qa.base.TestBase;
import com.qa.util.xlsUtility;

public class UpdateJSON extends TestUtil {
	
	TestBase testBase;
	xlsUtility datatable = null;
	
	public String updateCustomerDetails() {

		testBase = new TestBase();
		datatable = new xlsUtility(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\data\\data_UpdateCustomerDetail.xlsx");
		int intRowNum = 2;
		// Read data from JSON in the form of string 
			String usersJSONString = "";
			usersJSONString = TestUtil.get_JsonAsString("data_CreateCustomer");
	        
	    //Convert String into JSON object 
	        JSONObject responseJson = new JSONObject(usersJSONString);
	
	    //1. update customer consent information
	        if (testBase.prop.getProperty("update_Customer_Consent_Info").equals("true")) {
		        JSONObject responseJson_CustomerConsentInfo = responseJson.getJSONObject("customerConsentInfo");
		        JSONArray responseJson_Array_Consents = responseJson_CustomerConsentInfo.getJSONArray("consents");
		        //System.out.println("Contacts Array item --- >"+responseJson_Array_Consents.get(0));
		        JSONObject responseJson_Consents = responseJson_Array_Consents.getJSONObject(0);
		        JSONObject responseJson_Consent = responseJson_Consents.getJSONObject("consent");
		        if (responseJson_Consent.has("type")) {
		        	responseJson_Consent.remove("type");
		        	responseJson_Consent.put("type", datatable.getCellData("customer_details", "consent_type", intRowNum));	
		        }
		        if (responseJson_Consent.has("value")) {
		        	responseJson_Consent.remove("value");
		        	responseJson_Consent.put("value", datatable.getCellData("customer_details", "consent_value", intRowNum));	
		        }
		        if (responseJson_Consent.has("channel")) {
		        	responseJson_Consent.remove("channel");
		        	responseJson_Consent.put("channel", datatable.getCellData("customer_details", "consent_channel", intRowNum));	
		        }
		        if (responseJson_Consent.has("source")) {
		        	responseJson_Consent.remove("source");
		        	responseJson_Consent.put("source", datatable.getCellData("customer_details", "consent_source", intRowNum));	
		        }
	        } 
	        	
	    //2. update customer agreement information
	        if (testBase.prop.getProperty("update_Customer_Agreement_Info").equals("true")) {
	        	
	        }
	        
	    //3. update customer contact information    
	        if (testBase.prop.getProperty("update_Customer_Contact_Info").equals("true")) {
	        	JSONObject responseJson_CustomerContactInfo = responseJson.getJSONObject("customerContactInfo");
		        JSONArray responseJson_Array_Addresses = responseJson_CustomerContactInfo.getJSONArray("addresses");
		        //System.out.println("Contacts Array item --- >"+responseJson_Array_Addresses.get(0));
		        JSONObject responseJson_Addresses = responseJson_Array_Addresses.getJSONObject(0);
		        JSONObject responseJson_Address = responseJson_Addresses.getJSONObject("address");
		        if (responseJson_Address.has("country")) {
		        	responseJson_Address.remove("country");
		        	responseJson_Address.put("country", datatable.getCellData("customer_details", "contact_country", intRowNum));	
		        }
		        if (responseJson_Address.has("postCode")) {
		        	responseJson_Address.remove("postCode");
		        	responseJson_Address.put("postCode", datatable.getCellData("customer_details", "contact_postCode", intRowNum));	
		        }
		        if (responseJson_Address.has("postOffice")) {
		        	responseJson_Address.remove("postOffice");
		        	responseJson_Address.put("postOffice", datatable.getCellData("customer_details", "contact_postOffice", intRowNum));	
		        }
		        if (responseJson_Address.has("type")) {
		        	responseJson_Address.remove("type");
		        	responseJson_Address.put("type", datatable.getCellData("customer_details", "contact_type", intRowNum));	
		        }
		        if (responseJson_Address.has("type")) {
		        	responseJson_Address.remove("type");
		        	responseJson_Address.put("type", datatable.getCellData("customer_details", "contact_type", intRowNum));	
		        }  	
	        }
	        
		        
	    //Convert JSONObject back into String        
	        usersJSONString = responseJson.toString();
	        TestUtil.writeResult ("INFO", "Request String", usersJSONString);
	    // update JSON file with new details        
	        try {
	            Files.write(Paths.get(System.getProperty("user.dir")+"/src/main/java/com/qa/data/data_CreateCustomer.json"), usersJSONString.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return usersJSONString;
		
		
	} // Method updateCustomerDetails

	
} //Class
