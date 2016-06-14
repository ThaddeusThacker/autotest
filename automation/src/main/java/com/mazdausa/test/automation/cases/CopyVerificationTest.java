package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CopyVerificationTest extends BaseTest {

	private WebElement aElement;
	private WebElement bElement;
	private WebDriver appDriver;
	private WebDriver prodDriver;
    private String text_output;


	public CopyVerificationTest(WebDriver appDriver, WebDriver prodDriver) {
		this.appDriver = appDriver;
		this.prodDriver = prodDriver;

	}

	public Boolean execute(int copyContext, String copyValue){
		Boolean testResult =false;
		try
		{
			try
			{
				// get text on APP
				this.setDriver(appDriver);
				SearchContext contextPopup = new SearchContext(copyContext, copyValue);
				WebElement copyelement =  getWebElement(contextPopup);
				String appCopy = copyelement.getText();

				// get text on PROD
				this.setDriver(prodDriver);
				SearchContext contextPopup2 = new SearchContext(copyContext, copyValue);;
				copyelement =  getWebElement(contextPopup2);
				String prodCopy = copyelement.getText();

				// Compare both text
				testResult = appCopy.equals(prodCopy); //compare method

			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return testResult;
	}

	/**
	 *  This test is to compare COPY between approval and production when they have different properties (XPATH, Class..)
	 *
	 */

    public String getTextOutput(){

        return text_output;
    }

	public Boolean executeNoElementMatch(int copyContextApp, String copyValueApp, int copyContextProd, String copyValueProd){
		Boolean testResult =false;
		try
		{
			try
			{
				// get text on APP
				this.setDriver(appDriver);
				SearchContext contextPopup = new SearchContext(copyContextApp, copyValueApp);
				WebElement copyelement =  getWebElement(contextPopup);
				String appCopy = copyelement.getText();

				// get text on PROD
				this.setDriver(prodDriver);
				SearchContext contextPopup2 = new SearchContext(copyContextProd, copyValueProd);;
				copyelement =  getWebElement(contextPopup2);
				String prodCopy = copyelement.getText();

				// Compare both text
				testResult = appCopy.equals(prodCopy); //compare method
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}catch(Exception e){

			System.out.println(e.getMessage());
		}
		return testResult;
	}




//	public Boolean testCollection (WebElement parent, String search_type, String search_value, int copyContext, String copyValue){
//		Boolean test_result = true;
//		ArrayList<WebElement> elements;
//        String element_copy;
//        int element_count = 0;
//        text_output += "TESTING DOTS HOVER \n";
//		switch (search_type){
//			case "tag":
//				elements = (ArrayList<WebElement>) parent.findElements(By.tagName(search_value));
//				break;
//			case "class":
//				elements = (ArrayList<WebElement>) parent.findElements(By.className(search_value));
//				break;
//			default:
//				elements = new ArrayList<WebElement>();
//				break;
//		}
//		for(WebElement element: elements){
//            element_copy = element.getText();
//            String test_value = null;
//            try {
//                test_value = new String(elements.get(element_count));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
////            text_output += "testing copy for tooltip: " + dot_label + " -- " + test_value + " \n";
//            if(!element_copy.trim().equals(test_value)){
//                test_result = false;
//                text_output += "tooltip copy fail: " + element_copy + " -- " + test_value + " \n";
//            }
//            element_count++;
//		}
//		return test_result;
//	}
}

