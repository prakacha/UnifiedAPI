//Unified API - TestBase.java
package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	public int RESPONSE_STATUS_CODE_200= 200;	// OK
	public int RESPONSE_STATUS_CODE_201= 201;	// Successfully registered
	public int RESPONSE_STATUS_CODE_204= 204;	// No Content
	public int RESPONSE_STATUS_CODE_400= 400;	// Bad Request or Invalid Syntax
	public int RESPONSE_STATUS_CODE_401= 401;	// Unauthorized
	public int RESPONSE_STATUS_CODE_403= 403;	// Accessing the resource you were trying to reach is forbidden.
	public int RESPONSE_STATUS_CODE_404= 404;	// Not Found
	public int RESPONSE_STATUS_CODE_406= 406;	// Code 406 - Construct incorrect:
	public int RESPONSE_STATUS_CODE_500= 500;	// Internal Server Error. Server cannot process the request for an unknown reason
												

// reading the properties file
	public  Properties prop;
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream( System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			prop.load(ip);			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}	//	method TestBase	
	
}  //	class TestBase
