package com.mazdausa.test.automation.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
    private WebElement image_wrapper;

    public ExteriorColorTest(WebDriver webDriver) {

        this.setDriver(webDriver);
    }

    public boolean prepareTests(SearchContext searchContext, ArrayList<String> names,ArrayList<String> labels,SearchContext image_wrapper_context ){
        //Receive chips list and parent node
        boolean result = false;
        text_output = "PREPARING TEST: \n";
        chips_names = names;
        chips_labels = labels;
        parent_element = getWebElement(searchContext);
        image_wrapper = getWebElement(image_wrapper_context);
        if (parent_element != null) {
            chips = (ArrayList<WebElement>) parent_element.findElements(By.className("option"));
            text_output += "chips loaded \n";
            label_element = parent_element.findElement(By.className("option-text")).findElement(By.tagName("span"));
            if(label_element != null){
                result = true;
            }else{
                text_output += "label element not found \n";
                result = false;
            }
        }else{
            text_output += "parent element not found \n";
            result = false;
        }
        return result;
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
                test_result = false;
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
        text_output += "TESTING CHIPS IMAGE UPDATE \n";
        Boolean test_result = true;
        for(WebElement chip: chips){
            chip.click();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement image = image_wrapper.findElement(By.tagName("img"));
            if(image!=null){
                String source = image.getAttribute("src");
                WebElement tag_name = chip.findElement(By.tagName("span"));
                if (tag_name != null) {
                    String color_id = tag_name.getAttribute("class");
                    color_id = color_id.substring(6,color_id.length()-1);
                    if (!source.toLowerCase().contains(color_id.toLowerCase())) {
                        test_result = false;
                        text_output += "Wrong image for chip: " + color_id + "  \n";
                    }else{
                        text_output += "Image found for: " + color_id + "  \n";
                    }
                }
            }
        }
        return test_result;
    }

    public Boolean testChipsLayout(){
        //Receive chips list, parent node
        //For each chip in list, calculate position and validate order.
        text_output += "TESTING CHIPS LAYOUT \n";
        Boolean test_result = true;
        Boolean first_time = true;
        int prev_x = 0;
        int prev_y = 0;
        for(WebElement chip: chips){
            Point point = chip.getLocation();
            if(first_time){
                prev_x = point.getX();
                prev_y = point.getY();
                first_time = false;
            }else{
                if(point.getY() == prev_y && point.getX()>prev_x){
                    prev_x = point.getX();
                    prev_y = point.getY();
                }else {
                    test_result = false;
                    break;
                }
            }
        }
        return test_result;
    }


}
