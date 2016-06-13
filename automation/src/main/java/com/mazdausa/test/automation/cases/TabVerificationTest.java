package com.mazdausa.test.automation.cases;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mazdausa.test.util.TestUtil;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


/**
 * Created dellys.guillen
 */
public class TabVerificationTest extends BaseTest {


    private WebElement element = null;




    public TabVerificationTest(WebDriver webDriver) {

        this.setDriver(webDriver);
    }


    public Boolean test(int searchContext, String contextValue, String targetURL) {
        //window identification
        String mwh = driver.getWindowHandle();
        try {
            SearchContext clickdropdownLink = new SearchContext(searchContext, contextValue);
            element = getWebElement(clickdropdownLink);
            element.click();
            // opens identifications
            Set wins = driver.getWindowHandles();
            // iterate windows
            Iterator ite = wins.iterator();
            Thread.sleep(2500);
            // verify the windows
            while(ite.hasNext())
            {
                String popupHandle=ite.next().toString();
                if(!popupHandle.contains(mwh))
                {
                    driver.switchTo().window(popupHandle);
                    if (targetURL.compareTo(driver.getCurrentUrl()) == 0) {
                        testResult = true;
                    } else {
                        testResult = false;
                        System.out.println("Target URL = " + targetURL);
                        System.out.println("Current URL = " + driver.getCurrentUrl());

                    }
                    driver.close();
                    driver.switchTo().window(mwh);
                }
            }

        } catch (Exception e) {
            testResult = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return testResult;
    }
}