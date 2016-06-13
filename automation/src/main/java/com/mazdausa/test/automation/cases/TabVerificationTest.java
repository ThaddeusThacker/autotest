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
 * Created pablo.meza
 */
public class TabVerificationTest extends BaseTest {


    private WebElement element = null;




    public TabVerificationTest(WebDriver webDriver) {

        this.setDriver(webDriver);
    }


    public Boolean test(int searchContext, String contextValue, String targetURL) {
        String mwh = driver.getWindowHandle();
        try {

            SearchContext clickdropdownLink = new SearchContext(searchContext, contextValue);

            driver.findElement(By.id("nav-shop")).click();
            element = getWebElement(clickdropdownLink);
            element.click();
            Set wins = driver.getWindowHandles();
            Iterator ite = wins.iterator();

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while(ite.hasNext())
            {
                String popupHandle=ite.next().toString();
                if(!popupHandle.contains(mwh))
                {
                    driver.switchTo().window(popupHandle);
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