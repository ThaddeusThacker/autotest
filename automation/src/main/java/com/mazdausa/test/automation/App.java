package com.mazdausa.test.automation;
import com.mazdausa.test.util.TestUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.mazdausa.test.automation.cases.*;
import com.mazdausa.test.automation.cases.SearchContext;

import java.util.ArrayList;
import java.util.Arrays;
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

        prodDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        appDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		appDriver.get(vlpPageUrlApproval);
		prodDriver.get(vlpProdPageUrl);


		
		/* Set up Implicit Wait time before throwing an exception.
		 * See:  http://toolsqa.com/selenium-webdriver/wait-commands/
		 */

		appDriver.manage().window().maximize();
		prodDriver.manage().window().maximize();


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
                Alert alert2 = appDriver.switchTo().alert();
                alert2.accept();
            }
            catch (NoAlertPresentException e) {
                //System.out.println(e.toString());
                //e.printStackTrace();
                //do what you normally would if you didn't have the alert.
            }
        }

		SwitchContextTest switchContextProd = new SwitchContextTest(prodDriver);
		SwitchContextTest switchContextApp = new SwitchContextTest(appDriver);

        //test language popup

//		if(switchContextProd.changeContext(SearchContext.ID, props.getProperty("musa_homepage_frameId"))){
//            WebElement languageBtn = prodDriver.findElement(By.id(props.getProperty("musa_homepage_en_button")));
//            languageBtn.click();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            switchContextProd.backToDefault();
//        }
        WebElement lang_box;
        try {
             lang_box = appDriver.findElement(By.id(props.getProperty("musa_homepage_frameId")));

            if(lang_box != null && lang_box.isDisplayed()){
                if(switchContextApp.changeContext(SearchContext.ID, props.getProperty("musa_homepage_frameId"))){
                    WebElement languageBtn = appDriver.findElement(By.id(props.getProperty("musa_homepage_en_button")));
                    languageBtn.click();
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    switchContextApp.backToDefault();
                }
            }
        } catch (Exception e){

        }


		//ExteriorColorTest
        ArrayList<String> chips_names = new  ArrayList<String>(Arrays.asList(props.getProperty("m3h_exterior_chips_color_names").split(",")));
        ArrayList<String> chips_labels = new  ArrayList<String>(Arrays.asList(props.getProperty("m3h_exterior_chips_labels").split(",")));
        ExteriorColorTest exteriorColorTest = new ExteriorColorTest(appDriver);
		Boolean exteriorColorPrepare = exteriorColorTest.prepareTests(new SearchContext(SearchContext.XPATH, props.getProperty("m3h_exterior_chips_parent_node")), chips_names,chips_labels,new SearchContext(SearchContext.XPATH, props.getProperty("m3h_exterior_chips_wrapper")));
        Boolean exteriorColorTestDisplayed = exteriorColorTest.testChipsDisplayed();
        Boolean exteriorColorTestHovered = exteriorColorTest.testChipsHover();
		Boolean exteriorColorTestImages = exteriorColorTest.testChipsImageUpdate();
        Boolean exteriorColorTestLayout = exteriorColorTest.testChipsLayout();
        System.out.println(exteriorColorTest.getTextOutput());
		System.out.println("M3H Exterior Color Component exists: " + ((exteriorColorPrepare) ? "PASS" : "FAIL"));
        System.out.println("M3H Exterior Color Displayed: " + ((exteriorColorTestDisplayed) ? "PASS" : "FAIL"));
        System.out.println("M3H Exterior Color Hovered: " + ((exteriorColorTestHovered) ? "PASS" : "FAIL"));
		System.out.println("M3H Exterior Color Images: " + ((exteriorColorTestImages) ? "PASS" : "FAIL"));
        System.out.println("M3H Exterior Color Layout: " + ((exteriorColorTestLayout) ? "PASS" : "FAIL"));

		//InteriorColorTest
		InteriorColorTest intColorTest = new InteriorColorTest(appDriver);
		Boolean intColorTestResult = intColorTest.test("m3h", props, "http://images-approval.mazdausa.com/");
		System.out.println("Interior Color test: " + ((intColorTestResult) ? "PASS" : "FAIL"));

		//Open Popup disclaimer test
    	OpenPopupTest popupTest = new OpenPopupTest(appDriver);
		Boolean resultPopup = popupTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
		System.out.println("M3H disclaimer - Popup open: " + ((resultPopup) ? "PASS" : "FAIL"));
		OpenPopupTest popupTest2 = new OpenPopupTest(prodDriver);
		popupTest2.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));//

		//Scroll Test
		ElementScrollTest box = new ElementScrollTest(appDriver);
		Boolean resultScroll = box.test(SearchContext.XPATH, props.getProperty("m3h_hero_price_disclaimer_popup"));
		System.out.println("M3H disclaimer - Scrollbar: " + ((resultScroll) ? "PASS" : "FAIL"));

		// Mouse hover test
		MouseHoverTest mouseHover = new MouseHoverTest(appDriver);
		Boolean mouseHoverPresent = mouseHover.MousHoverTest(SearchContext.XPATH,props.getProperty("m3h_hero_price_disclaimer"));
		System.out.println("M3H disclaimer - Mouse Hover action: " + ((mouseHoverPresent) ? "PASS" : "FAIL"));

		//Disclaimer Copy test
		CopyVerificationTest copyVerify = new CopyVerificationTest(appDriver, prodDriver);
		Boolean copyResult = copyVerify.execute(SearchContext.XPATH,  props.getProperty("m3h_overview_price_disclaimer_textbox"));
		System.out.println("M3H disclaimer - Copy Result: " + ((copyResult)? "PASS" : "FAIL"));

		//Close Pop up disclaimer test
		ClosePopupTest closeTest = new ClosePopupTest(appDriver);
	    Boolean closeresult = closeTest.test(SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_close_link"), SearchContext.XPATH, props.getProperty("m3h_overview_price_disclaimer_popup"));
	    System.out.println("M3H disclaimer - Close Popup: " + ((closeresult) ? "PASS" : "FAIL"));

		//Overview headline / Sub headline copy test 
        CopyVerificationTest copyHeroVerify = new CopyVerificationTest(appDriver, prodDriver);
        Boolean copyHeadlineResult = copyHeroVerify.execute(SearchContext.XPATH, props.getProperty("m3h_header_copy"));
        System.out.println("M3H Copy Headline Result: " + ((copyHeadlineResult)? "PASS" : "FAIL"));
        Boolean copySubHeadlineResult = copyHeroVerify.execute(SearchContext.XPATH, props.getProperty("m3h_sub_head_copy"));
        System.out.println("M3H Copy Subheadline Result: " + ((copySubHeadlineResult)? "PASS" : "FAIL"));

        // Subheadline font test
        FontSubHeadlineTest fontsubheadlinetest = new FontSubHeadlineTest(appDriver);
        Boolean fontsubheadlinepresent = fontsubheadlinetest.FontSubHeadlineTest(SearchContext.XPATH,props.getProperty("m3h_sub_head_copy"));
        System.out.println("Subheadline font test M3H overview: " + ((fontsubheadlinepresent) ? "PASS" : "FAIL"));

        //Font Headline Test
        FontHeadlineTest fontheadlinetest = new FontHeadlineTest(appDriver);
        Boolean fontheadlinepresent = fontheadlinetest.FontHeadlineTest(SearchContext.XPATH,props.getProperty("m3h_header_copy"));
        System.out.println("Headline font test M3H overview: " + ((fontheadlinepresent) ? "PASS" : "FAIL"));

        //360 button Copy test
        CopyVerificationTest copyVerifyBtn = new CopyVerificationTest(appDriver, prodDriver);
        Boolean copyResultBtn = copyVerifyBtn.execute(SearchContext.XPATH, props.getProperty("m3h_360_button"));
        System.out.println("M3H Copy 360 button Result: " + ((copyResultBtn)? "PASS" : "FAIL"));

		//Hover 360 button
		PropertyOnHoverTest hover360Button = new PropertyOnHoverTest(appDriver);
		Boolean resultHover360Button = hover360Button.test(SearchContext.XPATH, props.getProperty("m3h_360_button"), "background-image");
		System.out.println("M3H Hover 360 button: " + ((resultHover360Button) ? "PASS" : "FAIL"));

		//Open 360 overlay
		OpenPopupTest overlay360Open = new OpenPopupTest(appDriver);
		Boolean resultOverlay360Open = overlay360Open.test(SearchContext.XPATH, props.getProperty("m3h_360_button"), SearchContext.XPATH, props.getProperty("m3h_360_overlay"));
		System.out.println("M3H Overlay 360 open: " + ((resultOverlay360Open) ? "PASS" : "FAIL"));

//		RotateExteriorImagesTest rotateTest = new RotateExteriorImagesTest(appDriver);
//		Boolean resultTest = rotateTest.test();

		//NavDotsTest
		ArrayList<String> dots_labels = new  ArrayList<String>(Arrays.asList(props.getProperty("m3h_dots_copy").split(",")));
		DotsVerificationTest navDotsTest = new DotsVerificationTest(appDriver, props, "m3h" );
		Boolean navDotsPrepare = navDotsTest.prepareTests(new SearchContext(SearchContext.XPATH, props.getProperty("m3h_dots_parent")), dots_labels);
        System.out.println("M3H Navigation Dots exists: " + ((navDotsPrepare) ? "PASS" : "FAIL"));
        Boolean navDotsTestCount = navDotsTest.testCountDots();
        System.out.println("M3H Navigation Dots Count: " + ((navDotsTestCount) ? "PASS" : "FAIL"));
        Boolean navDotsTestHovered = navDotsTest.testDotsHover();
        System.out.println("M3H Navigation Dots Hovered: " + ((navDotsTestHovered) ? "PASS" : "FAIL"));
        Boolean navDotsTestClick = navDotsTest.testDotsClick();
        System.out.println("M3H Navigation Dots Click: " + ((navDotsTestClick) ? "PASS" : "FAIL"));
        Boolean navDotsTestScroll = navDotsTest.testDotsScroll();
        System.out.println("M3H Navigation Dots Scroll: " + ((navDotsTestScroll) ? "PASS" : "FAIL"));
		//System.out.println(navDotsTest.getTextOutput());

		//Tertiary Nav Title Copy test
		CopyVerificationTest tertiaryCopyTest = new CopyVerificationTest(appDriver, prodDriver);
		Boolean tertiaryTitleCopyResult = tertiaryCopyTest.execute(SearchContext.XPATH,  props.getProperty("m3h_tertiarynav_title"));
		System.out.println("M3H Tertiary Nav Title  - Copy Result: " + ((tertiaryTitleCopyResult)? "PASS" : "FAIL"));

		//Tertiary Nav SubTitle Copy test
		Boolean tertiarySubtitleCopyResult = tertiaryCopyTest.execute(SearchContext.XPATH,  props.getProperty("m3h_tertiarynav_subtitle"));
		System.out.println("M3H Tertiary Nav Subtitle - Copy Result: " + ((tertiarySubtitleCopyResult)? "PASS" : "FAIL"));

		//Tertiary Nav disclaimer Copy test
		popupTest.test(SearchContext.XPATH, props.getProperty("m3h_tertiarynav_disclaimer_asterisk"), SearchContext.XPATH, props.getProperty("m3h_tertiarynav_disclaimer_copy_approval"));
		popupTest2.test(SearchContext.XPATH, props.getProperty("m3h_tertiarynav_disclaimer_asterisk"), SearchContext.XPATH, props.getProperty("m3h_tertiarynav_disclaimer_copy_production"));
		Boolean tertiaryDisclaimerCopyResult = tertiaryCopyTest.executeNoElementMatch(SearchContext.XPATH,  props.getProperty("m3h_tertiarynav_disclaimer_copy_approval"),SearchContext.XPATH,  props.getProperty("m3h_tertiarynav_disclaimer_copy_production"));
		System.out.println("M3H Tertiary Nav Disclaimer - Copy Result: " + ((tertiaryDisclaimerCopyResult)? "PASS" : "FAIL"));

		// Tertiary Nav onHover Test
		WebElement tertiaryParent = appDriver.findElement(By.xpath(props.getProperty("m3h_tertiarynav_container")));
		PropertyOnHoverTest tertiaryOnHoverTest = new PropertyOnHoverTest(appDriver);
		Boolean tertiaryHoverResult = tertiaryOnHoverTest.testCollection(tertiaryParent, "tag", "a", "color");
		System.out.println("M3H Hover Tertiary Nav: " + ((tertiaryHoverResult) ? "PASS" : "FAIL"));

		// Tertiary Nav Shop dropdown link
		OpenPopupTest tertiaryShopDropdown = new OpenPopupTest(appDriver);
		Boolean tertiaryShopDropdownResult = tertiaryShopDropdown.test(SearchContext.XPATH, props.getProperty("m3h_tertiarynav_shop"),SearchContext.XPATH, props.getProperty("m3h_tertiarynav_shop_dropdown"));
		System.out.println("M3H Tertiary Nav Shop Dropdown: " + ((tertiaryShopDropdownResult) ? "PASS" : "FAIL"));

		// Tertiary Nav Links
		LinkVerificationTest linkVerificationOverview = new LinkVerificationTest(appDriver);
		Boolean tertiaryOverviewTestResult = linkVerificationOverview.test(SearchContext.ID,props.getProperty("m3h_tertiarynav_overview"), props.getProperty("m3h_tertiarynav_overviewlink"));
		System.out.println("M3H Overview: Tertiary Nav test: " + ((tertiaryOverviewTestResult) ? "PASS" : "FAIL"));

		LinkVerificationTest linkVerificationSpecs = new LinkVerificationTest(appDriver);
		Boolean tertiarySpecsTestResult = linkVerificationSpecs.test(SearchContext.ID,props.getProperty("m3h_tertiarynav_specs"), props.getProperty("m3h_tertiarynav_specslink"));
		System.out.println("M3H Specs: Tertiary Nav test: " + ((tertiarySpecsTestResult) ? "PASS" : "FAIL"));

		LinkVerificationTest linkVerificationSearchInventory = new LinkVerificationTest(appDriver);
		Boolean tertiarySearchInventoryTestResult = linkVerificationSearchInventory.test(SearchContext.XPATH,props.getProperty("m3h_tertiarynav_searchinventory"), props.getProperty("m3h_tertiarynav_searchinventorylink"));
		System.out.println("M3H Search Inventory: Tertiary Nav test: " + ((tertiarySearchInventoryTestResult) ? "PASS" : "FAIL"));
		appDriver.get(vlpPageUrlApproval);

		LinkVerificationTest linkVerificationRaq = new LinkVerificationTest(appDriver);
		Boolean tertiaryRaqTestResult = linkVerificationRaq.test(SearchContext.XPATH,props.getProperty("m3h_tertiarynav_raq"), props.getProperty("m3h_tertiarynav_raqlink"));
		System.out.println("M3H RAQ: Tertiary Nav test: " + ((tertiaryRaqTestResult) ? "PASS" : "FAIL"));
		appDriver.get(vlpPageUrlApproval);

		LinkVerificationTest linkVerificationBtv = new LinkVerificationTest(appDriver);
		Boolean tertiaryBtvTestResult = linkVerificationBtv.test(SearchContext.XPATH,props.getProperty("m3h_tertiarynav_buildandprice"), props.getProperty("m3h_tertiarynav_buildandpricelink"));
		System.out.println("M3H BTV: Tertiary Nav test: " + ((tertiaryBtvTestResult) ? "PASS" : "FAIL"));
		appDriver.get(vlpPageUrlApproval);


        System.out.println("----------------------------------------------------------------------");
	}

}
