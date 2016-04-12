package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CopyVerificationTest extends BaseTest {

    private WebElement aElement;
    private WebElement bElement;


    public CopyVerificationTest(WebDriver driver) {

        this.setDriver(driver);

    }

    public Boolean execute(int linkContext, String linkValue, int copyContext, String copyValue){
        Boolean testResult =false;
        try
        {
            try
            {
                // get text on QA

                driver.get("http://musa.qaserver.devteamcr.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");

                SearchContext contextLink = new SearchContext(linkContext, linkValue);
                WebElement link = getWebElement(contextLink);
                link.click();
                Thread.sleep(500);
                SearchContext contextPopup = new SearchContext(copyContext, copyValue);
                WebElement copyelement =  getWebElement(contextPopup);
                String bCopy = copyelement.getText();

                // get text on PROD
                driver.get("http://mazdausa.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");
                contextLink = new SearchContext(linkContext, linkValue);
                link = getWebElement(contextLink);
                link.click();
                Thread.sleep(500);
                contextPopup = new SearchContext(copyContext, copyValue);
                copyelement =  getWebElement(contextPopup);
                String aCopy = copyelement.getText();

                // Compare both text
                testResult = bCopy.equals(aCopy); //compare method


            }catch(Exception e){

                System.out.println(e.getMessage());

            }

        }catch(Exception e){

            System.out.println(e.getMessage());

        }
        return testResult;

    }



}