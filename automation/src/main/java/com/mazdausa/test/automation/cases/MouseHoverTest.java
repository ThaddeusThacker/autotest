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
	   public Boolean execute(){
		   Boolean testResult = false;
		   try{
			   driver.get("http://mazdausa.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");
			   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			   driver.manage().window().maximize();
			   
			   element = driver.findElement(By.xpath("//*[@id='overview']/div[1]/div/p[1]/span"));
			   
			   Actions myAction = new Actions(driver);
			   myAction.moveToElement(element).build().perform();
			   
			   element.click();
			         
			    testResult = true; 
						
		    }catch(Exception e){
						
			System.out.println(e.getMessage());
		
		    } 
		   
		   return testResult;
	
	}

}
