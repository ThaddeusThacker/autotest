package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CopyVerificationTest extends BaseTest {
	
	private WebElement aElement;
	private WebElement bElement;


	public CopyVerificationTest(WebDriver driver) {
		
		this.setDriver(driver);

		}
	
	public Boolean execute(){
		Boolean testResult =false;
		try
		{
			try
			{
				// get text on QA
				
				driver.get("http://musa.qaserver.devteamcr.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");
			    WebElement link2 = driver.findElement(By.xpath("//*[@id='overview']/div[1]/div/p[1]/span"));
				link2.click();
				WebElement aelement =  driver.findElement(By.xpath("html/body/footer/div/div[2]/div/div/div"));
				String bCopy = aelement.getText();
				
				// get text on PROD
				driver.get("http://mazdausa.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");
				WebElement link = driver.findElement(By.xpath("//*[@id='overview']/div[1]/div/p[1]/span"));
				link.click();
				WebElement element =  driver.findElement(By.xpath("html/body/footer/div/div[2]/div/div/div"));
				String aCopy = element.getText();
				
				// Compare both text
			    testResult = bCopy.equals(aCopy); //compare method 
			    
			    
			}catch(Exception e){
				
				System.out.println(e.getMessage());
				
			}
				
			}catch(Exception e){
			
			System.out.println(e.getMessage());
			
		}
		return testResult;
		
	}
	
	

}


