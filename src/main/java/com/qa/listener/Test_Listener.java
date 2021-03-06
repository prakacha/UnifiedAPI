package com.qa.listener;

import org.testng.ITestContext ;		
import org.testng.ITestListener ;		
import org.testng.ITestResult ;
import com.qa.util.TestUtil;

public class Test_Listener implements ITestListener {

//Declare result variables	
	int strTestStatus;
	String strTestName;

	public void onTestSuccess(ITestResult result) {
		strTestStatus = result.getStatus();
		strTestName = result.getName();
		System.out.println("Summary: Test '"+strTestName+"' "+ "passed");
		TestUtil.getResult(strTestStatus, strTestName);	
	}

	public void onTestFailure(ITestResult result) {
		strTestStatus = result.getStatus();
		strTestName = result.getName();
		System.out.println("Summary: Test '"+strTestName+"' "+ "failed");
		TestUtil.getResult(strTestStatus, strTestName);
	}

	public void onTestSkipped(ITestResult result) {
		strTestStatus = result.getStatus();
		strTestName = result.getName();
		System.out.println("Summary: Test '"+strTestName+"' "+ "skipped");
		TestUtil.getResult(strTestStatus, strTestName);	
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		
	}

	public void onFinish(ITestContext context) {
			
	}
	
	public void onTestStart(ITestResult result) {
		
	}
}
