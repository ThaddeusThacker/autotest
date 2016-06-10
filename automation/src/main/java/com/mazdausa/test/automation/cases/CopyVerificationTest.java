package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CopyVerificationTest extends BaseTest {

	private WebElement aElement;
	private WebElement bElement;
	private WebDriver appDriver;
	private WebDriver prodDriver;


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


}