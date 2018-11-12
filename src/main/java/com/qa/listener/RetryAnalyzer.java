package com.qa.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	int counter = 0;
	int retryLimit = 3;		// set the counter to retry failed test cases
	public boolean retry(ITestResult result) {
		if (counter < retryLimit)	{
			counter++;
			return true;
		} //if
		return false;	
	} // Method retry
	
} // class
