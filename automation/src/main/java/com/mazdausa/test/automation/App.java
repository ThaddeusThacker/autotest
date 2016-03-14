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
		Boolean resultPopup = popupTest.test(SearchContext.XPATH, "//*[@id=\"overview\"]/div[1]/div/p[1]/span", SearchContext.XPATH, "/html/body/footer/div/div[2]/div/div");
		System.out.println("Popup open: " + ((resultPopup) ? "PASS" : "FAIL"));
		
	
		

		ElementScrollTest box = new ElementScrollTest(prodDriver);
		Boolean resultScroll = box.test(SearchContext.XPATH, props.getProperty("m3h_hero_price_disclaimer_popup"));
		System.out.println("Scrollbar: " + ((resultScroll) ? "PASS" : "FAIL"));
		
		
		
		
		
		
		// test2
		// test3
		
		switchContext.backToDefault();
		
		

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
		
		// Mouse hover test
		MouseHoverTest mouseHover = new MouseHoverTest(prodDriver);
		Boolean mouseHoverPresent = mouseHover.execute();
		System.out.println("Mouse Hover action: " + ((mouseHoverPresent) ? "PASS" : "FAIL"));
		
		
		
		
/*		
		WebDriver prodDriver = new HtmlUnitDriver();
		prodDriver.get(vlpProdPageUrl);

		WebDriver approvalDriver = new HtmlUnitDriver();
		approvalDriver.get(vlpNonProdPageUrl);

		System.out.println("Prod Current URL = " + prodDriver.getCurrentUrl());
		System.out.println("Prod Title = " + prodDriver.getTitle());
		System.out.println("Non Prod Current URL = " + approvalDriver.getCurrentUrl());
		System.out.println("Non Prod Title = " + approvalDriver.getTitle());

		// Test getting a list of specific tag types
		List<org.openqa.selenium.WebElement> options = prodDriver.findElements(By.tagName("div"));

		if (options != null && options.size() > 0) {
			WebElement element = null;
			String elementName = null;
			for (int i = 0; i < options.size(); i++) {
				element = options.get(i);
				elementName = element.toString();

				System.out.println("Element ToString = " + elementName);

			}

		}
*/
		
	    
		
		CopyVerificationTest copyVerify = new CopyVerificationTest(prodDriver);
		
		Boolean copyResult = copyVerify.execute();
		System.out.println("Copy Result: " + ((copyResult)? "Pass" : "Fail"));
		// Dellys part
		
		ClosePopupTest closeTest = new ClosePopupTest(prodDriver);
	    Boolean closeresult = closeTest.test(SearchContext.XPATH, "/html/body/footer/div/div[2]/div/div/span[1]", SearchContext.XPATH, "/html/body/footer/div/div[2]/div/div");
	    System.out.println("Close Popup: " + ((closeresult) ? "PASS" : "FAIL"));
	    //Pablo
		
		System.out.println("----------------------------------------------------------------------");

		/*
		 * String xpathExpression = props.getProperty("m3h_vlp_overview_nav");
		 * 
		 * // Let's get the nav buttons WebElement overviewNavLink =
		 * prodDriver.findElement(By.xpath(xpathExpression)); if(overviewNavLink
		 * != null) { System.out.println("Overview Nav = " +
		 * overviewNavLink.toString());
		 * 
		 * }
		 */


	}
}
