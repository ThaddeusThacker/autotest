package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Properties;

public class DotsVerificationTest extends BaseTest {

    private WebElement parent;
    private Properties props;
    private String Code;
    private ArrayList<WebElement> dots;
    //private WebElement parent_element;
    private String text_output;
    private ArrayList<String> dots_names;
    private WebElement label_element;
    // private ArrayList<String> chips_labels;
    // private WebElement image_wrapper;


    public DotsVerificationTest(WebDriver webDriver, Properties p, String carCode) {
        props = p;
        this.setDriver(webDriver);
        Code = carCode;

    }

    public boolean prepareTests(SearchContext searchContext, ArrayList<String> names){
        //,ArrayList<String> labels){
        //Receive chips list and parent node
        boolean result = false;
        text_output = "PREPARING TEST: \n";
        dots_names = names;
        //chips_labels = labels;
        parent = getWebElement(searchContext);
        //image_wrapper = getWebElement(image_wrapper_context);
        if (parent != null) {
            dots = (ArrayList<WebElement>) parent.findElements(By.className(","));
            text_output += "dots copy loaded \n";
            label_element = parent.findElement(By.className("option-text")).findElement(By.tagName("span"));
            if(label_element != null){
                result = true;
            }else{
                text_output += "label element not found \n";
                result = false;
            }
        }else{
            text_output += "parent element not found \n";
            result = false;
        }
        return result;
    }

    public Boolean test(SearchContext searchContext) {
        Boolean testResult = true;
        String CarCopy = props.getProperty(Code + "_dots_copy");
        String CarCount =  props.getProperty(Code + "_dots_count");
        // CarCopy is a copy list declared on properties file and it will be the copy variable for each dots
        // CarCount is the number of the dots declared on properties
        try
        {
            parent = getWebElement(searchContext);
            ArrayList<WebElement> arrayDots = (ArrayList<WebElement>) parent.findElements(By.tagName("li"));
            if (arrayDots.size() != Integer.parseInt(CarCount))
            {
                testResult = false;
            }



//            else
//            {
//                for (int i = 0; i < arrayDots.size(); i++)
//                {
//                    if (arrayDots.get(i).getCssValue("cursor").equals("pointer"))
//                    {
//                        testResult = true;
//                        if (arrayDots.get(i).getText() == props.getProperty("3h_dots_copy"))
//                        {
//
//                        }
//
//                    }
//
//                }
//            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            testResult = false;
        }
        return testResult;
    }

    public Boolean test(int searchContext, String contextValue, String cssProperty){
        testResult = false;
        try
        {
            SearchContext context = new SearchContext(searchContext, contextValue);
            parent = getWebElement(context);
            String initialPropertyValue = parent.getCssValue(cssProperty);
            Actions action = new Actions(driver);
            action.moveToElement(parent).build().perform();
            Thread.sleep(100);
            String hoveredPropertyValue = parent.getCssValue(cssProperty);
            if(!initialPropertyValue.equals(hoveredPropertyValue)){
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