package com.mazdausa.test.automation.cases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class FontSubHeadlineTest  extends BaseTest{

    private WebElement element;

    public FontSubHeadlineTest(WebDriver driver) {
        this.setDriver(driver);
    }
    public Boolean FontSubHeadlineTest (int searchContext, String contextValue){
        Boolean testResult = false;
        try{
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            SearchContext myContext = new SearchContext(searchContext, contextValue);
            element = getWebElement(myContext);
            if(element.getCssValue("font-family").equals("interstate-light,interstatemazda-light,sans-serif,arial")
                    ||element.getCssValue("color").equals("#fff" )
                    ||element.getCssValue("font-size").equals("1.5em" )){;

                testResult = true;

            }

        }catch(Exception e){

            System.out.println(e.getMessage());

        }

        return testResult;

    }

}