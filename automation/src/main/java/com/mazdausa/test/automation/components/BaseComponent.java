package com.mazdausa.test.automation.components;

import org.openqa.selenium.WebDriver;

import com.mazdausa.test.automation.BaseWebObject;

public class BaseComponent extends BaseWebObject
{
	private String name ;
	private String type ; 
	private String siteName ;
	
	private String vehicleCode ;
	private String locationUrl ;
	private String xPath ;
	
	public BaseComponent()
	{ 
	}
	
	public BaseComponent(WebDriver driver)
	{
		super.setWebDriver(driver);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getLocationUrl() {
		return locationUrl;
	}
	public void setLocationUrl(String locationUrl) {
		this.locationUrl = locationUrl;
	}
	public String getXPath() {
		return xPath;
	}
	public void setXPath(String xPath) {
		this.xPath = xPath;
	}


}
