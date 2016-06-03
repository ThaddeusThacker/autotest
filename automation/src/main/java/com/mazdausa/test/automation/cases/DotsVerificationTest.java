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
    private String text_output;
    private ArrayList<String> dots_labels;



    public DotsVerificationTest(WebDriver webDriver, Properties p, String carCode) {
        props = p;
        this.setDriver(webDriver);
        Code = carCode;

    }

    public boolean prepareTests(SearchContext searchContext, ArrayList<String> labels){
        //,ArrayList<String> labels){
        //Receive dots list and parent node
        boolean result = false;
        text_output = "PREPARING TEST: \n";
        dots_labels = labels;
        parent = getWebElement(searchContext);

        if (parent != null) {
            dots = (ArrayList<WebElement>) parent.findElements(By.tagName("li"));
            text_output += "dots copy loaded \n";
            result = true;
        }else{
            text_output += "parent element not found \n";
            result = false;
        }
        return result;
    }

    public String getTextOutput(){

        return text_output;
    }

    public Boolean testCountDots() {
        Boolean test_result = true;
        int car_count =  Integer.parseInt(props.getProperty(Code + "_dots_count"));
        // CarCount is the number of the dots declared on properties
        if(dots.size() != car_count){
            test_result = false;
        }
        return test_result;
    }

    public Boolean testDotsHover() {
        //For each dot in list, hover element and test background color and right copy.
        text_output += "TESTING DOTS HOVER \n";
        Boolean test_result = true;
        String initial_background_value;
        String hovered_background_value;
        String dot_label;
        int label_count = 0;
        WebElement tooltip;
        for(WebElement dot: dots){
            //Obtain the background color
            initial_background_value = dot.getCssValue("background-color");
            //Hover
            Actions action = new Actions(driver);
            action.moveToElement(dot).build().perform();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Obtain the hovered background color
            hovered_background_value = dot.getCssValue("background-color");
            //Check background color
            if(!initial_background_value.equals(hovered_background_value)){
                test_result = false;
            }
            //Check cursor pointer
            if(!dot.getCssValue("cursor").equals("pointer")){
                test_result = false;
            }
            //Check label visible
            tooltip = dot.findElement(By.className("tooltip"));
            if(!tooltip.isDisplayed()){
                test_result = false;
            }
            //Check label copy
            dot_label = dot.findElement(By.className("text")).getText();
            if(!dot_label.equals(dots_labels.get(label_count))){
                test_result = false;
            }
            label_count++;
        }
        return test_result;
    }

    public Boolean testDotsClick() {
        //For each dot in list, click element
        //Check dot URL
        text_output += "TESTING DOTS CLICK \n";
        Boolean test_result = true;
        for(WebElement dot: dots){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dot.findElement(By.tagName("a")).click();
            String hash = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf('#'));
            if(!hash.equals(dot.findElement(By.tagName("a")).getAttribute("href"))){
                test_result = false;
            }
        }
        return test_result;
    }

    public Boolean testDotsScroll() {
        //For each dot in list, click element
        //Check dot URL
        text_output += "TESTING DOTS SCROLL \n";
        Boolean test_result = true;
        String panel_id;
        WebElement panel;
        WebElement tooltip;
        String initial_background_value;
        String hovered_background_value;
        for(WebElement dot: dots){
            initial_background_value = dot.getCssValue("background-color");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel_id = dot.findElement(By.tagName("a")).getAttribute("href");
            panel_id = panel_id.substring(panel_id.indexOf("#") + 1);
            panel = driver.findElement(By.id(panel_id));
            if(panel != null){
                Actions action = new Actions(driver);
                action.moveToElement(panel).build().perform();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Check label visible
                tooltip = dot.findElement(By.className("tooltip"));
                if(!tooltip.isDisplayed()){
                    test_result = false;
                }
                //Check background color
                hovered_background_value = dot.getCssValue("background-color");
                if(!initial_background_value.equals(hovered_background_value)){
                    test_result = false;
                }
            }else{
                text_output += "unable to find panel:" + panel_id + " \n";
                test_result = false;
            }
        }
        return test_result;
    }

}