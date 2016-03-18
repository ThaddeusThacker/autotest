package com.mazdausa.test.automation.cases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseHoverTest  extends BaseTest{
    
	private WebElement element;
	
	public MouseHoverTest(WebDriver driver) {
	       this.setDriver(driver);
		}
		public Boolean MousHoverTest (int searchContext, String contextValue){
			Boolean testResult = false;
			try{
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().maximize();

				SearchContext myContext = new SearchContext(searchContext, contextValue);
				element = getWebElement(myContext);

				if(element.getCssValue("cursor").equals("pointer")){;

					testResult = true;

				}
							
			    }catch(Exception e){
							
				System.out.println(e.getMessage());
			
			    } 
			   
			   return testResult;
	
	}

}
