package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class RotateExteriorImagesTest extends BaseTest{
	
	public RotateExteriorImagesTest(WebDriver driver) {
	       this.setDriver(driver);
	}
	
	public Boolean test (){
		System.out.println("360 frame was open");
		
		WebElement button =  driver.findElement(By.id("extViewBtn4"));
		button.click();
		
	
		
		WebElement source = driver.findElement(By.xpath("//*[@id=\"exterior360\"]/div/div[1]"));
		
		WebElement target = driver.findElement(By.xpath("//*[@id=\"exterior360\"]/div/div[3]/a[1]"));
		
		
		Actions builder = new Actions(driver);

		Action dragAndDrop = builder.clickAndHold(source)
		   .moveToElement(target)
		   .release(target)
		   .build();

		dragAndDrop.perform();
		
		
		
		
		
		return testResult;
	    
		
		
	
	}

}
