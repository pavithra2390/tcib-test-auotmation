package com.temenos.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



/**
 * Util class consists wait for page load,page load with user defined max time and is used globally in all classes and methods
 * 
 */
public class ElementLayer {
	
	private final WebDriver driver;
	
//	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
//	public static int maxElementWait = 25;
	
	public ElementLayer(WebDriver driver) {
		this.driver = driver;
	}

//	/**
//	 * waitForPageLoad waits for the page load with default page load wait time
//	 * 
//	 * @param driver
//	 *            : Webdriver
//	 */
//	public static void waitForPageLoad(final WebDriver driver) {
//		waitForPageLoad(driver, WebDriverFactory.maxPageLoadWait);
//	}

//	/**
//	 * waitForPageLoad waits for the page load with custom page load wait time
//	 * 
//	 * @param driver
//	 *            : Webdriver
//	 * @param maxWait
//	 *            : Max wait duration
//	 */
//	public static void waitForPageLoad(final WebDriver driver, int maxWait) {
//		//long startTime = StopWatch.startTime();
//		FluentWait <WebDriver> wait = new WebDriverWait(driver, maxWait).pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class).withMessage("Page Load Timed Out");
//		try {
//
//			if (configProperty.getProperty("documentLoad").equalsIgnoreCase("true"))
//				wait.until(WebDriverFactory.documentLoad);
//
//			if (configProperty.getProperty("imageLoad").equalsIgnoreCase("true"))
//				wait.until(WebDriverFactory.imagesLoad);
//
//			if (configProperty.getProperty("framesLoad").equalsIgnoreCase("true"))
//				wait.until(WebDriverFactory.framesLoad);
//
//			String title = driver.getTitle().toLowerCase();
//			String url = driver.getCurrentUrl().toLowerCase();
//			Log.event("Page URL:: " + url);
//
//			if ("the page cannot be found".equalsIgnoreCase(title) || title.contains("is not available") || url.contains("/error/") || url.toLowerCase().contains("/errorpage/")) {
//				Assert.fail("Site is down. [Title: " + title + ", URL:" + url + "]");
//			}
//		}
//		catch (TimeoutException e) {
//			driver.navigate().refresh();
//			wait.until(WebDriverFactory.documentLoad);
//			wait.until(WebDriverFactory.imagesLoad);
//			wait.until(WebDriverFactory.framesLoad);
//		}
//		//Log.event("Page Load Wait: (Sync)", StopWatch.elapsedTime(startTime));
//
//	} // waitForPageLoad
	
	public static boolean compareTwoList(List<String> expectedElements, List<String> actualElements) {
		boolean statusToBeReturned = false;
		List<String> uniqueList = new ArrayList<String>();
		List<String> missedList = new ArrayList<String>();
		for (String item : expectedElements) {
			if (actualElements.contains(item)) {
				uniqueList.add(item);
			} else {
				missedList.add(item);
			}
		}
		/*
		 * Collections.sort(expectedElements); Collections.sort(actualElements);
		 */
		if (expectedElements.equals(actualElements)) {
			Log.event("All elements checked on this page:: " + uniqueList);
			statusToBeReturned = true;
		} else {
			Log.failsoft("Missing element on this page:: " + missedList);
			statusToBeReturned = false;
		}
		return statusToBeReturned;
	}

	/**	
	 * To verify the list of page elements are displayed
	 * @param expectedElements
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean VerifyPageElementDisplayed(List<String> expectedElements, Object obj)throws Exception{
		/*WebElement element = getElement(elementName, obj);
		if(element.isDisplayed() == true)
			return true;
		else
			return false;*/

		List<String> actual_elements = new ArrayList<String>();
		for (String expEle : expectedElements) {
			Field f = null;
			try {
				f = obj.getClass().getDeclaredField(expEle);
				f.setAccessible(true);
			} catch (NoSuchFieldException | SecurityException e1) {
				throw new Exception("No such a field present on this page, Please check the expected list values:: " + expEle);
			}
			WebElement element = null;
			try {
				element = ((WebElement) f.get(obj));
			//	BrowserActions.scrollToViewElement(element, driver);
				if (element.isDisplayed()) {
					actual_elements.add(expEle);
				}
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				Log.exception(e1);
			} catch (Exception e) {
				e.printStackTrace();
				
			}

		}
		return compareTwoList(expectedElements, actual_elements);
	}
	
	
	
}
