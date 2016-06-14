package com.mazdausa.test.automation.cases;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;



	public class RotateExteriorImagesTest extends BaseTest{

		private WebElement element;
		private String[] exteriorColorList;
		private String[] interiorColors;
		
		
    	public RotateExteriorImagesTest(WebDriver driver) {

    		this.setDriver(driver);

    	}


    	private void getExteriorProperties(String carCode, Properties props){
		
    		String extColors = props.getProperty(carCode + "_exterior_chips");
		exteriorColorList =  extColors.split(",");
		
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

		public Boolean test(String carCode, Properties props, String server){

			SearchContext switchFrame = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_360_button"));
			WebElement rotateFrame = getWebElement(switchFrame);
			rotateFrame.click();

			System.out.println("360 frame was open");

    			try{
        		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        		getExteriorProperties(carCode, props);
        		
        			SearchContext imageWrapper = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_imagewrapper"));
        			WebElement source = getWebElement(imageWrapper);
		
        			for (String color :exteriorColorList){
        				SearchContext colorVerification = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_chip_ext_" + color));
        				element = getWebElement(colorVerification);
        				element.click();

        				String intColors = props.getProperty(carCode + "_" + color + "_interior");
        				String[] interiorColorList = intColors.split(",");

        				for (String interiorColor :interiorColorList){
        				SearchContext intColorVerification = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_" + interiorColor + "_interior"));
        				WebElement interiorElement = getWebElement(intColorVerification);
        				interiorElement.click();

        				SearchContext extImageContext = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_exterior_background"));

        					for(int b=1; b<=18; b++){
        					WebElement exteriorImage = getWebElement(extImageContext);
        					String imageURL = exteriorImage.getAttribute("src");

        					String intURL = props.getProperty(carCode + "_" + color + "_exterior_" + interiorColor + "_"+ b);
        					System.out.println("M3H Exterior images: " + imageURL + " is different from property: " + server + intURL);
        					System.out.println(imageURL);

        						if(imageURL.compareTo(server + intURL) != 0){
        						testResult = false;
        						
        						System.out.println("M3H Exterior images: " + imageURL + " is different from property: " + server + intURL);

        						}

        						holdAndRelease(source, 60, 0);

        					}

        				}

        			}

        }catch(Exception e){

     
        	testResult=false;
        	System.out.println(e); 
        	e.printStackTrace();

	}	 


        return testResult;


	}



}