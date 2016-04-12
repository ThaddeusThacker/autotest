package com.mazdausa.test.automation;
import com.mazdausa.test.util.TestUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.mazdausa.test.automation.cases.*;
import com.mazdausa.test.automation.cases.SearchContext;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import com.mazdausa.test.automation.components.CopyTest;
//import com.mazdausa.test.pages.VehicleLandingPage;

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
        String homePageUrlApproval = null;
		String homePageUrlNonProd = null;
		String vlpProdPageUrl = null;
		String vlpNonProdPageUrl = null;
		String vlpPageUrlApproval = null;

		List vehicleCodes = null;

		// Test to see if i read the props
		if (props != null) {
			// all is good, lets read some properties
			environment = props.getProperty("musa_environment");
			homePageUrlApproval = props.getProperty("musa_homepage_url_approval");
			vlpPageUrlApproval = props.getProperty("m3h_vlp_url_approval");
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
		WebDriver appDriver = new FirefoxDriver();
		WebDriver prodDriver = new FirefoxDriver();

		appDriver.get(vlpPageUrlApproval);
		prodDriver.get(vlpProdPageUrl);

		
		/* Set up Implicit Wait time before throwing an exception.
		 * See:  http://toolsqa.com/selenium-webdriver/wait-commands/
		 */
		prodDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		appDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//appDriver.manage().window().maximize();
		//prodDriver.manage().window().maximize();


		//Authentication on approval
		if (appDriver.getCurrentUrl().contains("portaltest")){

			System.out.println("Redirect to validation approval " + appDriver.getCurrentUrl());
			WebElement user = appDriver.findElement(By.xpath(props.getProperty("user_field")));
			user.sendKeys(props.getProperty("user"));
			WebElement pass = appDriver.findElement(By.xpath(props.getProperty("pass_field")));
			pass.sendKeys(props.getProperty("pass"));
			WebElement logonBtn = appDriver.findElement(By.xpath(props.getProperty("logon_button")));
			logonBtn.click();

			try {
				Alert alert = appDriver.switchTo().alert();
				alert.accept();
				//if alert present, accept and move on.
				appDriver.get(vlpPageUrlApproval);
			}
			catch (NoAlertPresentException e) {
				//do what you normally would if you didn't have the alert.
			}
		}


		SwitchContextTest switchContext = new SwitchContextTest(prodDriver);

        //test language popup

		if(switchContext.changeContext(SearchContext.ID, props.getProperty("musa_homepage_frameId"))){
            WebElement languageBtn = prodDriver.findElement(By.id(props.getProperty("musa_homepage_en_button")));
            languageBtn.click();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switchContext.backToDefault();
        }




		//Open Popup disclaimer test
		OpenPopupTest popupTest = new OpenPopupTest(appDriver);
		Boolean resultPopup = popupTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
		System.out.println("Popup open: " + ((resultPopup) ? "PASS" : "FAIL"));

		//Scroll Test
		ElementScrollTest box = new ElementScrollTest(appDriver);
		Boolean resultScroll = box.test(SearchContext.XPATH, props.getProperty("m3h_hero_price_disclaimer_popup"));
		System.out.println("Scrollbar: " + ((resultScroll) ? "PASS" : "FAIL"));

		// Mouse hover test
		MouseHoverTest mouseHover = new MouseHoverTest(appDriver);
		Boolean mouseHoverPresent = mouseHover.MousHoverTest(SearchContext.XPATH,props.getProperty("m3h_hero_price_disclaimer"));
		System.out.println("Mouse Hover action: " + ((mouseHoverPresent) ? "PASS" : "FAIL"));

		//Disclaimer Copy test
		CopyVerificationTest copyVerify = new CopyVerificationTest(appDriver,prodDriver);
		Boolean copyResult = copyVerify.execute(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_copy"), SearchContext.XPATH,  props.getProperty("m3h_overview_price_disclaimer_textbox"));
		System.out.println("Copy Result: " + ((copyResult)? "PASS" : "FAIL"));

		//Close Pop up disclaimer test
		ClosePopupTest closeTest = new ClosePopupTest(appDriver);
	    Boolean closeresult = closeTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_close_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
	    System.out.println("Close Popup: " + ((closeresult) ? "PASS" : "FAIL"));

		//Hover 360 button
		PropertyOnHoverTest hover360Button = new PropertyOnHoverTest(appDriver);
		Boolean resultHover360Button = hover360Button.test(SearchContext.XPATH, props.getProperty("m3h_360_button"), "background-image");
		System.out.println("Hover 360 button: " + ((resultHover360Button) ? "PASS" : "FAIL"));

		//Open 360 overlay
		OpenPopupTest overlay360Open = new OpenPopupTest(appDriver);
		Boolean resultOverlay360Open = overlay360Open.test(SearchContext.XPATH, props.getProperty("m3h_360_button"), SearchContext.XPATH, props.getProperty("m3h_360_overlay"));
		System.out.println("Overlay 360 open: " + ((resultOverlay360Open) ? "PASS" : "FAIL"));


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
