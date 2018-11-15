package com.qa.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.qa.base.TestBase;

public class Retry_Listener implements IAnnotationTransformer{

	TestBase TestBase_transform;
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		
	//to create multiple customer
		TestBase_transform = new TestBase();
		String str_customer_Count = TestBase_transform.prop.getProperty("CreateCustomer_Count");
		int int_customer_Count = Integer.parseInt(str_customer_Count);
		if(int_customer_Count > 1) {
			if ("test_Post_API_Create".equals(testMethod.getName())) {			// run only create-customer 
			      annotation.setInvocationCount(int_customer_Count);
			    }
			if (!"test_Post_API_Create".equals(testMethod.getName())) {			// do not run any other test
			      annotation.setEnabled(false);;
			    }	
		}
		
	// retry the failed test cases
		annotation.setRetryAnalyzer(RetryAnalyzer.class);	
		
	} //Method transform
	
} //class
