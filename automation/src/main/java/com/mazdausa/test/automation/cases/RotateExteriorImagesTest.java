package com.mazdausa.test.automation.cases;

		import java.util.ArrayList;
		import java.util.Properties;
		import java.util.concurrent.TimeUnit;
		import org.openqa.selenium.By;
		import org.openqa.selenium.WebDriver;
		import org.openqa.selenium.WebElement;
		import org.openqa.selenium.interactions.Action;
		import org.openqa.selenium.interactions.Actions;



public class RotateExteriorImagesTest extends BaseTest{

	private WebElement element;
	private String[] exteriorColorList;
	private String[] interiorColors;


	public RotateExteriorImagesTest(WebDriver driver) {

		this.setDriver(driver);

	}

	/**
	 * Gets the properties to run the test
	 * @param carCode Vehicle code
	 * @param props Properties file
     */

	private void getExteriorProperties(String carCode, Properties props){

		String extColors = props.getProperty(carCode + "_exterior_chips");
		exteriorColorList =  extColors.split(",");

	}

    /**
	 * Method that builds and set the parameters that will be used to perform the hold and drag action on each image.
	 * @param element Object that will perform the action.
	 * @param x Variable that sets the x coordinate as a positive value to perform a rotation to the right.
	 * @param y Variable that sets the y coordinate as a negative value to perform a rotation to the left.
     */
	private void holdAndRelease(WebElement element, int x, int y){

		Actions action = new Actions(driver);
		Action dragAndDrop = action.moveToElement(element)
				.clickAndHold()
				.moveByOffset(x*-1, y)
				.release()
				.build();

		dragAndDrop.perform();
	}

	private int visibleImage(ArrayList<WebElement> children){
		int result = 1;
		for(WebElement child:children){
			if(child.isDisplayed()){
				break;
			}
			result++;
		}
		return result;
	}

	private String getVisibleImageSrc(ArrayList<WebElement> children){
		String result = "";
		for(WebElement child:children){
			if(child.isDisplayed()){
				result = child.getAttribute("src");
				break;
			}
		}
		return result;
	}

    /**
	 * Method that test the default rotation clockwise on each exterior color when opening the 360 frame.
	 * @param carCode
	 * @param propsM3H
	 * @return Either if the first and last images are the respective ones within the default clockwise rotation.
     */

	public Boolean clockWiseTest(String carCode, Properties propsM3H){
		int firstImageLoaded;
		int lastImageLoaded;

		try{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getExteriorProperties(carCode, propsM3H);

			for (String color :exteriorColorList) {

				SearchContext extColorChips = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_chip_ext_" + color));
				element = getWebElement(extColorChips);
				element.click();

				SearchContext switchFrame = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_360_button"));
				WebElement rotateFrame = getWebElement(switchFrame);
				rotateFrame.click();

				//System.out.println("360 frame was open");

				WebElement imageParent = getWebElement(new SearchContext(SearchContext.XPATH,propsM3H.getProperty(carCode + "_360_images_container")));
				ArrayList<WebElement> children = (ArrayList<WebElement>) imageParent.findElements(By.tagName("img"));

				firstImageLoaded = visibleImage(children);

				System.out.println(" First image was loaded " + firstImageLoaded + " for color" + color);

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lastImageLoaded = visibleImage(children);
				System.out.println(" Last image was loaded " + lastImageLoaded + " for color" + color);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
						if(firstImageLoaded == lastImageLoaded){
							System.out.println("Images are the same " + firstImageLoaded + " + " + lastImageLoaded);
							testResult = false;
							break;
						}
				SearchContext closeLink = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_close_rotate_frame"));
				WebElement rotateFrame_2 = getWebElement(closeLink);
				rotateFrame_2.click();
			}

		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
		return testResult;
	}

	/**
	 * Thist method performs the testing of the rotation for each image on the 360 frame including the interior colors
	 * @param carCode
	 * @param propsM3H
	 * @param server
     * @return Either if the test pass or fails.
     */

	public Boolean test(String carCode, Properties propsM3H, String server){

		SearchContext switchFrame = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_360_button"));
		WebElement rotateFrame = getWebElement(switchFrame);
		rotateFrame.click();

		System.out.println("360 frame was open");

		try{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getExteriorProperties(carCode, propsM3H);

			SearchContext imageWrapper = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_imagewrapper"));
			WebElement source = getWebElement(imageWrapper);

			for (String color :exteriorColorList){
				SearchContext colorVerification = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_chip_ext360_" + color));
				element = getWebElement(colorVerification);
				element.click();

				String intColors = propsM3H.getProperty(carCode + "_" + color + "_interior");
				String[] interiorColorList = intColors.split(",");

				for (String interiorColor :interiorColorList){
					SearchContext intColorVerification = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_" + interiorColor + "_interior360"));
					WebElement interiorElement = getWebElement(intColorVerification);
					interiorElement.click();

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					//   SearchContext extImageContext = new SearchContext(SearchContext.XPATH, propsM3H.getProperty(carCode + "_exterior_background"));

					//   System.out.println("Array for " + carCode + "_" + color + "_exterior_" + interiorColor);
					String UrlImage = propsM3H.getProperty(carCode + "_" + color + "_exterior_" + interiorColor);
					ArrayList<String> images = new ArrayList<String>();

					for(int i=0, images_length=18; i<images_length; i++){
						images.add(UrlImage.replace("##", String.format("%02d", i+1)));
					}

					WebElement imageParent = getWebElement(new SearchContext(SearchContext.XPATH,propsM3H.getProperty(carCode + "_360_images_container")));
					ArrayList<WebElement> children = (ArrayList<WebElement>) imageParent.findElements(By.tagName("img"));

					int currentNumber = visibleImage(children);
					//System.out.println("Current number: " + currentNumber);

					for (int i=currentNumber-1; i>0; i--){
						String shifted = images.remove(0);
						images.add(shifted);
					}

//                            for(String image:images){
//                                System.out.println("image array: " + image);
//                            }
					for(int b=0; b<18; b++){
						String imageURL = getVisibleImageSrc(children);
						//System.out.println("image from browser: " + imageURL);

						if(imageURL.compareTo(server + images.get(b)) != 0){
							testResult = false;
							System.out.println("M3H Exterior images: " + imageURL + " is different from property: " + server + images.get(b));
						}
						holdAndRelease(source, 60, 0);
					}
				}
			}
		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
		return testResult;
	}



}