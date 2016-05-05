package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;


public class ExteriorColorTest extends BaseTest {

    private ArrayList<WebElement> chips;
    private WebElement parent_element;
    private String text_output;
    private ArrayList<String> chips_names;
    private WebElement label_element;
    private ArrayList<String> chips_labels;

    public ExteriorColorTest(WebDriver webDriver) {

        this.setDriver(webDriver);
    }

    public Boolean prepareTests(SearchContext searchContext, ArrayList<String> names,ArrayList<String> labels){
        //Receive chips list and parent node
        text_output = "PREPARING TEST: \n";
        chips_names = names;
        chips_labels = labels;
        parent_element = getWebElement(searchContext);
        if (parent_element != null) {
            chips = (ArrayList<WebElement>) parent_element.findElements(By.className("option"));
            text_output += "chips loaded \n";
            label_element = parent_element.findElement(By.className("option-text")).findElement(By.tagName("span"));
            if(label_element != null){
                return true;
            }else{
                text_output += "label element not found \n";
                return false;

            }

        }else{
            text_output += "parent element not found \n";
            return false;

        }
    }

    public String getTextOutput(){

        return text_output;
    }

    public Boolean testChipsDisplayed(){
        //Receive chips list and parent node
        //For each chip in list, find element and test isDisplayed?
        text_output += "TESTING CHIPS DISPLAYED \n";
        Boolean test_result = true;
        for(WebElement chip: chips){
            if(!chip.isDisplayed()){
                test_result = false;
                text_output += "Chip not displayed: " +chip.getAttribute("data-analytics-cta-detail")+ "  \n";
            }else{ //chip displayed, test in list name
                WebElement tag_name = chip.findElement(By.tagName("span"));
                if (tag_name != null) {
                    if (!chips_names.contains((String)tag_name.getAttribute("class"))) {
                        test_result = false;
                        text_output += "Chip not found in list: " + tag_name.getAttribute("class")+ "  \n";
                    }
                }
            }
        }
        return test_result;
    }

    public Boolean testChipsHover() {
        //Receive chips list, parent node, color names
        //For each chip in list, hover element and test red ring and right copy.
        text_output += "TESTING CHIPS HOVER \n";
        Boolean test_result = true;
        for(WebElement chip: chips){
            String initialPropertyValue = chip.getCssValue("background-position");
            Actions action = new Actions(driver);
            action.moveToElement(chip).build().perform();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String hoveredPropertyValue = chip.getCssValue("background-position");
            if(initialPropertyValue.equals(hoveredPropertyValue) && !chip.getAttribute("class").contains("selected")){
                text_output += "Can't detect red ring: " +chip.getAttribute("data-analytics-cta-detail")+ "  \n";
                testResult = false;
            }
            //Test label copy
//            if(label_element!= null) {
//                if (!chips_labels.contains(label_element.getText())) {
//                    text_output += "Chip label not found for: " + chip.getAttribute("data-analytics-cta-detail") + " , " + label_element.getText() + "   \n";
//                    testResult = false;
//                }
//            }
        }
        return test_result;
    }


    public Boolean testChipsImageUpdate(){
        //Receive chips list, parent node, image names
        //For each chip in list, click element and test image update.
        return true;

    }

    public Boolean testChipsLayout(){
        //Receive chips list, parent node
        //For each chip in list, calculate position and validate order.
        return true;

    }


}
