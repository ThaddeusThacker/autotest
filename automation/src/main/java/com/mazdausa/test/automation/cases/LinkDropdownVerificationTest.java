package com.mazdausa.test.automation.cases;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mazdausa.test.util.TestUtil;

import java.util.Properties;


/**
 * Created pablo.meza
 */
public class LinkDropdownVerificationTest extends BaseTest {



    private WebElement element = null;


    public LinkDropdownVerificationTest(WebDriver webDriver) {
        this.setDriver(webDriver);
    }





    public Boolean test(int searchContext, String contextValue, String targetURL,boolean alertBoolean) {

        try {



            SearchContext clickdropdownLink = new SearchContext(searchContext, contextValue);

            driver.findElement(By.id("nav-shop")).click();
            element = getWebElement(clickdropdownLink);
            element.click();
            try {
                Thread.sleep(2500);
                if (alertBoolean) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                }
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