//Unified API - RestClient.java
package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {		
	
	
	//1. POST method to create a new customer in MDM // first create closable-http-response method		
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{	// API url, paylod(that contains JSON file details), and headerMAP(e.g content type, userid etc)
		// creates HttpClients object, that will be responsible to execute the url		
		CloseableHttpClient httpClient = HttpClients.createDefault();	
		HttpPost httppost = new HttpPost(url);	//creates http post request for the given API url
		//Create Entity pay-load(JSON)		
		httppost.setEntity(new StringEntity(entityString));	// setEntity method is used to define the pay-load, the data that we are going to pass through JSON		
		//create header
		for (Map.Entry<String, String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);	//hit API
		return closeableHttpResponse;			
	}	//Method post	
	
	
	
	//2. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;		
	}	//Method Get
	
	
	
	//3. GET Method with Headers:
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		//create header
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;		
	}	//Method Get
	
	
	
	//4. PUT Method:
	public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url); //http put request
		httpput.setEntity(new StringEntity(entityString)); //for pay-load
		//for headers:
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpput.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpput);
		return closebaleHttpResponse;			
	}	
	
	
	//5. Delete Method:	
		//to be written
		
} 	// class RestClient