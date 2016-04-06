package com.mazdausa.test.automation.cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OpenPopupTest extends BaseTest {
	
	private WebElement element;
	
	public OpenPopupTest(WebDriver webDriver) {
		this.setDriver(webDriver);
	}
	
	public Boolean test(int searchContext, String contextValue, int popupContext, String popupValue){
		testResult = false;
		try
		{
			SearchContext context = new SearchContext(searchContext, contextValue);
			element = getWebElement(context);	
			element.click();
			Thread.sleep(500);
			SearchContext popupC = new SearchContext(popupContext, popupValue);
			WebElement popupE = getWebElement(popupC);
			
			if(popupE.isDisplayed()){
				testResult = true;
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());

		}
		
		return testResult;
	}

}
