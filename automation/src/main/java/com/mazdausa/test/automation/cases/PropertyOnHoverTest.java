package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

public class PropertyOnHoverTest extends BaseTest {

    private WebElement element;

    public PropertyOnHoverTest(WebDriver webDriver) {

        this.setDriver(webDriver);
    }

    public Boolean test(int searchContext, String contextValue, String cssProperty){
        testResult = false;
        try
        {
            SearchContext context = new SearchContext(searchContext, contextValue);
            element = getWebElement(context);
            String initialPropertyValue = element.getCssValue(cssProperty);
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
            Thread.sleep(100);
            String hoveredPropertyValue = element.getCssValue(cssProperty);
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

    public Boolean testCollection (WebElement parent, String search_type, String search_value, String search_property){
        Boolean test_result = true;
        ArrayList<WebElement> elements;
        switch (search_type){
            case "tag":
                elements = (ArrayList<WebElement>) parent.findElements(By.tagName(search_value));
                break;
            case "class":
                elements = (ArrayList<WebElement>) parent.findElements(By.className(search_value));
                break;
            default:
                elements = new ArrayList<WebElement>();
                break;
        }
        for(WebElement element: elements){
            try {
                String initialPropertyValue = element.getCssValue(search_property);
                Actions action = new Actions(driver);
                action.moveToElement(element).build().perform();
                Thread.sleep(100);
                String hoveredPropertyValue = element.getCssValue(search_property);
                if(initialPropertyValue.equals(hoveredPropertyValue)){
                    test_result = false;
                }
            } catch(Exception ex) {
                System.out.println(ex.getMessage());

            }
        }
        return test_result;
    }
}
