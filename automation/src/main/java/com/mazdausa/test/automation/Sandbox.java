package com.mazdausa.test.automation;

import com.mazdausa.test.automation.cases.ClickVerificationTest;
import com.mazdausa.test.automation.cases.RotateExteriorImagesTest;
import com.mazdausa.test.automation.cases.SearchContext;
import com.mazdausa.test.automation.cases.SwitchContextTest;
import com.mazdausa.test.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class Sandbox {

	public static void main(String[] args) {
		
		
		TestUtil util = new TestUtil();

		util.getConfigProperties("cars.properties");
		util.getConfigProperties("m3h_vlp_properties");
		Properties props = util.getConfigProperties("cars.properties");
		Properties propsM3H = util.getConfigProperties("m3h_vlp_properties");
		
		
		WebDriver prodDriver = new FirefoxDriver();
		prodDriver.get("http://www.mazdausa.com/MusaWeb/displayPage.action?pageParameter=modelsMain&vehicleCode=M3H#overview");

		RotateExteriorImagesTest test = new RotateExteriorImagesTest(prodDriver);
		test.test("m3h", propsM3H, props.getProperty("musa_images_prod_url"));
		
//		SwitchContextTest switchContext = new SwitchContextTest(prodDriver);
//		
//		switchContext.changeContext(SearchContext.ID, props.getProperty("musa_homepage_en_buttonId"));
//		
//		
//		
//		ClickVerificationTest clickVerify = new ClickVerificationTest(prodDriver);
//		clickVerify.test(SearchContext.XPATH, "");
//		
//		// test2
//		// test3
//		// test4
//		
//		switchContext.backToDefault();


	}

}
