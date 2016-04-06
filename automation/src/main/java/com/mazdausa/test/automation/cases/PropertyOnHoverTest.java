package com.mazdausa.test.automation.cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
}
