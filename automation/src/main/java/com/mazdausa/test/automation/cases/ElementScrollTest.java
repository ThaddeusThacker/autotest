package com.mazdausa.test.automation.cases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementScrollTest extends BaseTest {

	private WebElement element;
	
	public ElementScrollTest(WebDriver webDriver) {
		this.setDriver(webDriver);
	}

	public Boolean test(int searchContext, String contextValue){
		Boolean result = false;
		try
		{
			SearchContext context = new SearchContext(searchContext, contextValue);
			element = getWebElement(context);			
			
			if(element.getCssValue("overflow-y").equals("auto") || element.getCssValue("overflow-y").equals("scroll")){
				result = true;
			}
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());

		}
		
		return result;
	}
}
