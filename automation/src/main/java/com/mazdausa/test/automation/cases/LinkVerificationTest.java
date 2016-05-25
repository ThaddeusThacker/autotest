package com.mazdausa.test.automation.cases;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Properties;


/**
 * Created by alina.viquez
 */
public class LinkVerificationTest extends BaseTest {

    private WebElement element = null;


    public LinkVerificationTest(WebDriver webDriver) {
        this.setDriver(webDriver);
    }


    public Boolean test(int searchContext, String contextValue, String targetURL) {

        try {


            SearchContext clickLink = new SearchContext(searchContext, contextValue);
            element = getWebElement(clickLink);
            element.click();


            if (targetURL.compareTo(driver.getCurrentUrl()) == 0) {
                testResult = true;


            } else {
                testResult = false;

            }



        } catch (Exception e) {
            testResult = false;
            System.out.println(e);
            e.printStackTrace();
        }

        return testResult;
    }
}