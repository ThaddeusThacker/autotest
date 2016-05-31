package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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
        text_output += "TESTING DOTS COUNT \n";
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
            initial_background_value = dot.findElement(By.className("circle")).getCssValue("background-position");
            //Hover
            Actions action = new Actions(driver);
            action.moveToElement(dot.findElement(By.tagName("a"))).build().perform();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Obtain the hovered background color
            hovered_background_value = dot.findElement(By.className("circle")).getCssValue("background-position");
            //Check background color
            if(!dot.findElement(By.tagName("a")).getAttribute("class").contains("active")){
                if(initial_background_value.equals(hovered_background_value)){
                    test_result = false;
                    text_output += "Initial color: " + initial_background_value + " -- Hovered color: " + hovered_background_value + " \n";
                }
            }
            //Check cursor pointer
            if(!dot.findElement(By.tagName("a")).getCssValue("cursor").equals("pointer")){
                test_result = false;
            }
            //Check label visible
            tooltip = dot.findElement(By.className("tooltip"));
            if(!tooltip.isDisplayed()){
                test_result = false;
                text_output += "tooltip not visible: " + tooltip.findElement(By.className("text")).getText() + " \n";
            }
            //Check label copy
            dot_label = dot.findElement(By.className("text")).getText();
            String test_value = null;
            try {
                test_value = new String(dots_labels.get(label_count).getBytes("ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            text_output += "testing copy for tooltip: " + dot_label + " -- " + test_value + " \n";
            if(!dot_label.trim().equals(test_value)){
                test_result = false;
                text_output += "tooltip copy fail: " + dot_label + " -- " + test_value + " \n";
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
            dot.findElement(By.tagName("a")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String hash = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf('#'));
            if(!hash.equals(dot.findElement(By.tagName("a")).getAttribute("href").substring(driver.getCurrentUrl().indexOf('#')))){
                test_result = false;
                text_output += "hash copy: " + hash + " -- " + dot.findElement(By.tagName("a")).getAttribute("href") + " \n";
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
        Collections.reverse(dots);
        for(WebElement dot: dots){
            initial_background_value = dot.findElement(By.className("circle")).getCssValue("background-position");
            try {
                Thread.sleep(500);
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
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Check label visible
                tooltip = dot.findElement(By.className("tooltip"));
                if(!tooltip.isDisplayed()){
                    test_result = false;
                    text_output += "tooltip not visible: " + tooltip.findElement(By.className("text")).getText() + "," + panel_id + " \n";
                }
                //Check background color
                hovered_background_value = dot.findElement(By.className("circle")).getCssValue("background-position");
                if(!dot.findElement(By.tagName("a")).getAttribute("class").contains("active")){
                    if(initial_background_value.equals(hovered_background_value)){
                        test_result = false;
                        text_output += "Initial color: " + initial_background_value + " -- Hovered color: " + hovered_background_value + " \n";
                    }
                }
            }else{
                text_output += "unable to find panel:" + panel_id + " \n";
                test_result = false;
            }
        }
        return test_result;
    }

}