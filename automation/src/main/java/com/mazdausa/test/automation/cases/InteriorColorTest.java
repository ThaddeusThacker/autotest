package com.mazdausa.test.automation.cases;

import java.awt.Desktop.Action;
import java.awt.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class InteriorColorTest extends BaseTest{

		 private WebElement element;
		 
		 private String[] exteriorColorList;
		 private String[] cssPropertyList;
		 private Map<String, String> cssPropertyHash;

		 public InteriorColorTest(WebDriver webDriver) {
		        this.setDriver(webDriver);      
		 }
         
		 private void getInteriorProperties(String carCode, Properties props){
			 
			 cssPropertyHash = new Hashtable();
			 
			 String extColors = props.getProperty(carCode + "_exterior_chips");
			 exteriorColorList =  extColors.split(",");
			 
			 String cssProperties = props.getProperty(carCode + "_cssProperties");
			 cssPropertyList =  cssProperties.split(",");
			 
			 for(String properties:cssPropertyList){
				 String[] cssProperty = properties.split(":");
				 cssPropertyHash.put(cssProperty[0], cssProperty[1]);
			 
			 }
		 }
		 
		 public Boolean test(String carCode, Properties props, String server){
			 try{
				 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				 getInteriorProperties(carCode, props);
			 
		    		 
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
					     
					     
					     SearchContext intImageContext = new SearchContext(SearchContext.XPATH, props.getProperty(carCode + "_backgroundImages"));
					     WebElement interiorImage = getWebElement(intImageContext);
					     String imageURL = interiorImage.getAttribute("src");
					     
					     String intURL = props.getProperty(carCode + "_" + color + "_interior_" + interiorColor);
					     if(imageURL.compareTo(server + intURL) != 0){
					          testResult = false;
					          System.out.println("Interior images name: " + imageURL + " is different from property: " + server + intURL);
					     }
					     
					    
					     for(int i=0; i< cssPropertyList.length; i++){
					    	 Iterator iterator = cssPropertyHash.entrySet().iterator();
					    	 while (iterator.hasNext()) {
					    		 Map.Entry pair = (Map.Entry)iterator.next();
					    		 if(!interiorElement.getCssValue((String)pair.getKey()).equals((String)pair.getValue())){
					    		    testResult=false;
					    		    System.out.println("Property: " + (String)pair.getKey() + ":" + interiorElement.getCssValue((String)pair.getKey()) + 
					    		    				  " is different from property: " + (String)pair.getKey() + ":" + (String)pair.getValue());
					    		 }
					    	 }
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
 
