package com.mazdausa.test.automation;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;

import com.mazdausa.test.automation.cases.ClickVerificationTest;
import com.mazdausa.test.automation.cases.ClosePopupTest;
import com.mazdausa.test.automation.cases.CopyVerificationTest;
import com.mazdausa.test.automation.cases.ElementScrollTest;
import com.mazdausa.test.automation.cases.MouseHoverTest;
import com.mazdausa.test.automation.cases.OpenPopupTest;
import com.mazdausa.test.automation.cases.SearchContext;
import com.mazdausa.test.automation.cases.SwitchContextTest;
import com.mazdausa.test.automation.components.Disclaimer;
import com.mazdausa.test.automation.panels.OverviewPanel;
import com.mazdausa.test.pages.PageObject;
//import com.mazdausa.test.automation.components.CopyTest;
//import com.mazdausa.test.pages.VehicleLandingPage;
import com.mazdausa.test.util.TestUtil;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello Tester!");

		// Open the configuration file which will point to all vehicle config
		// files.
		TestUtil util = new TestUtil();

		util.getConfigProperties("cars.properties");

		// Open the vehicle properties config files
		Properties props = util.getConfigProperties("cars.properties");

		String environment = null;
		String homePageUrlProd = null;
		String homePageUrlNonProd = null;
		String vlpProdPageUrl = null;
		String vlpNonProdPageUrl = null;

		List vehicleCodes = null;

		// Test to see if i read the props
		if (props != null) {
			// all is good, lets read some properties
			environment = props.getProperty("musa_environment");
			homePageUrlProd = props.getProperty("m3h_vlp_url_prod");
			homePageUrlNonProd = props.getProperty("musa_homepage_url_" + environment);
			vlpProdPageUrl = props.getProperty("m3h_vlp_url_prod");
			vlpNonProdPageUrl = props.getProperty("m3h_vlp_url_" + environment);

			vehicleCodes = util.getStringTokens("vehicle_codes");

			System.out.println("Props loaded successfully. Size = " + props.size());
			System.out.println("Props Environment = " + environment);
			System.out.println("MUSA Homepage URL Production = " + homePageUrlProd);
			System.out.println("MUSA Homepage URL Test Environment = " + homePageUrlNonProd);
			System.out.println("Vehicle Codes Count = " + vehicleCodes.size());

		} else {
			// no bueno. Properties did not get loaded
			System.out.println("No bueno.  Properties did not get loaded");
		}

		// Create a new instance of a driver
		WebDriver prodDriver = new FirefoxDriver();
		
		prodDriver.get(homePageUrlProd);
		
		/* Set up Implicit Wait time before throwing an exception.
		 * See:  http://toolsqa.com/selenium-webdriver/wait-commands/
		 */
		prodDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		
		//prodDriver.get(props.getProperty("musa_homepage_frameId"));
		
		SwitchContextTest switchContext = new SwitchContextTest(prodDriver);
		
	//	switchContext.changeContext(SearchContext.ID, props.getProperty("musa_homepage_en_button"));
		
		

		//ali
		OpenPopupTest popupTest = new OpenPopupTest(prodDriver);
		
		Boolean resultPopup = popupTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
		
		System.out.println("Popup open: " + ((resultPopup) ? "PASS" : "FAIL"));
	
		ElementScrollTest box = new ElementScrollTest(prodDriver);
		Boolean resultScroll = box.test(SearchContext.XPATH, props.getProperty("m3h_hero_price_disclaimer_popup"));
		System.out.println("Scrollbar: " + ((resultScroll) ? "PASS" : "FAIL"));
		
		
		switchContext.backToDefault();
		
		
		// Mouse hover test
		MouseHoverTest mouseHover = new MouseHoverTest(prodDriver);
		Boolean mouseHoverPresent = mouseHover.MousHoverTest(SearchContext.XPATH,props.getProperty("m3h_hero_price_disclaimer"));
		System.out.println("Mouse Hover action: " + ((mouseHoverPresent) ? "PASS" : "FAIL"));
		

		
		CopyVerificationTest copyVerify = new CopyVerificationTest(prodDriver);
		Boolean copyResult = copyVerify.execute(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_copy"), SearchContext.XPATH,  props.getProperty("m3h_overview_price_disclaimer_textbox"));
		System.out.println("Copy Result: " + ((copyResult)? "PASS" : "FAIL"));
		// Dellys part
		
		ClosePopupTest closeTest = new ClosePopupTest(prodDriver);		
	    Boolean closeresult = closeTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_close_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
	    System.out.println("Close Popup: " + ((closeresult) ? "PASS" : "FAIL"));
	    //Pablo
		
		System.out.println("----------------------------------------------------------------------");


		
		

		/*
		 * Create a new instance of the VLP Nav page class and initialize any
		 * WebElement fields in it.
		 */
		//VehicleLandingPage vlpNavProd = new VehicleLandingPage(prodDriver, vlpProdPageUrl);

		// Check the car title to see if it matches what we expect.
		//Disclaimer priceDisclaimer = vlpNavProd.getPriceDisclaimer();

		//System.out.println("Successfully got Price Disclaimer! : " + priceDisclaimer.toString());

		// Get Panel names

		// Get Overview panel
		//OverviewPanel overview = new OverPanel()

		// Get headline copy

		// Get disclaimer

		// Test Homepage

		// Test Mazda 6 VLP

		// Test Copy

		// Test Disclaimer

		// Test 360 Rotation	

	}
}
