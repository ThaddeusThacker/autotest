package com.mazdausa.test.automation;

import com.mazdausa.test.automation.cases.ClickVerificationTest;
import com.mazdausa.test.automation.cases.SearchContext;
import com.mazdausa.test.automation.cases.SwitchContextTest;
import com.mazdausa.test.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class Sandbox {

	public static void main(String[] args) {
		
		
		//Test commit to branch Rubio
		TestUtil util = new TestUtil();

		util.getConfigProperties("cars.properties");
		Properties props = util.getConfigProperties("cars.properties");
		
		
		WebDriver prodDriver = new FirefoxDriver();
		prodDriver.get(props.getProperty("musa_homepage_frameId"));

		SwitchContextTest switchContext = new SwitchContextTest(prodDriver);
		
		switchContext.changeContext(SearchContext.ID, props.getProperty("musa_homepage_en_buttonId"));
		
		
		
		ClickVerificationTest clickVerify = new ClickVerificationTest(prodDriver);
		clickVerify.test(SearchContext.XPATH, "");
		
		// test2
		// test3
		// test4
		
		switchContext.backToDefault();


	}

}
