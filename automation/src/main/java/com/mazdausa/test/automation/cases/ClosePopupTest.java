package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ClosePopupTest extends BaseTest{

private WebElement element;
 
 public ClosePopupTest(WebDriver webDriver) {
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

   
  		if(driver.findElements(By.xpath(popupValue)).isEmpty()){
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