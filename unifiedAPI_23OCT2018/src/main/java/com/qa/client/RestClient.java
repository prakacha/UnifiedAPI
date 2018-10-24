//Unified API - RestClient.java
package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {		
// POST method to create a new customer in MDM
// create closable-http-response method		
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{	// API url, paylod(that contains JSON file details), and headerMAP(e.g content type, userid etc)
// creates HttpClients object, that will be responsible to execute the url		
		CloseableHttpClient httpClient = HttpClients.createDefault();	
		HttpPost httppost = new HttpPost(url);	//creates http post request for the given API url
//Create Entity pay-load(JSON)		
		httppost.setEntity(new StringEntity(entityString));	// setEntity method is used to define the payload, the data that we are going to pass through JSON		
//create header
		for (Map.Entry<String, String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
//hit API		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;	
		
	}	//Method post	
	
} 	// class RestClient