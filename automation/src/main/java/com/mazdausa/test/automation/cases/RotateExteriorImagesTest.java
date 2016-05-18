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
	
	private void holdAndRelease(WebElement element, int x, int y){
		Actions action = new Actions(driver);

		Action dragAndDrop = action.moveToElement(element)
		   .clickAndHold()
		   .moveByOffset(x, y)
		   .release()
		   .build();

		dragAndDrop.perform();
	}
	
	public Boolean test (){
		System.out.println("360 frame was open");
		
		WebElement button =  driver.findElement(By.id("extViewBtn4"));
		button.click();
		
	
		
		WebElement source = driver.findElement(By.xpath("//*[@id=\"exterior360\"]/div/div[1]"));
		
		holdAndRelease(source, 60, 0);
		holdAndRelease(source, 60, 0);
		holdAndRelease(source, 60, 0);
		holdAndRelease(source, 60, 0);
		holdAndRelease(source, 60, 0);
		holdAndRelease(source, 60, 0);
				
		
		return testResult;
	    
		
		
	
	}

}
