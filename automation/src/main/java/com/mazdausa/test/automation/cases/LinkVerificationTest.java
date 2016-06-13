package com.mazdausa.test.automation.cases;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Properties;


/**
 *
 * This test is to call each Tertiary Nav link, and verify the path of each link.
 *
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
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (targetURL.compareTo(driver.getCurrentUrl()) == 0) {
                testResult = true;
            } else {
                testResult = false;
                System.out.println("Target URL = " + targetURL);
                System.out.println("Current URL = " + driver.getCurrentUrl());
            }
        } catch (Exception e) {
            testResult = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return testResult;
    }
}