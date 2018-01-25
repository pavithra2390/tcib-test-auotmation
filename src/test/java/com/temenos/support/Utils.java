package com.temenos.support;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Util class consists wait for page load,page load with user defined max time
 * and is used globally in all classes and methods
 * 
 */
public class Utils {
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader
			.getInstance();
	public static int maxElementWait = 25;

	/**
	 * waitForPageLoad waits for the page load with default page load wait time
	 * 
	 * @param driver
	 *            : Webdriver
	 */
	public static void waitForPageLoad(final WebDriver driver) {
		waitForPageLoad(driver, WebDriverFactory.maxPageLoadWait);
	}

	/**
	 * waitForPageLoad waits for the page load with custom page load wait time
	 * 
	 * @param driver
	 *            : Webdriver
	 * @param maxWait
	 *            : Max wait duration
	 */
	public static void waitForPageLoad(final WebDriver driver, int maxWait) {
		// long startTime = StopWatch.startTime();
		FluentWait<WebDriver> wait = new WebDriverWait(driver, maxWait)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("Page Load Timed Out");
		try {

			if (configProperty.getProperty("documentLoad").equalsIgnoreCase(
					"true"))
				wait.until(WebDriverFactory.documentLoad);

			if (configProperty.getProperty("imageLoad")
					.equalsIgnoreCase("true"))
				wait.until(WebDriverFactory.imagesLoad);

			if (configProperty.getProperty("framesLoad").equalsIgnoreCase(
					"true"))
				wait.until(WebDriverFactory.framesLoad);

			String title = driver.getTitle().toLowerCase();
			String url = driver.getCurrentUrl().toLowerCase();

			System.out.println("Page URL:: " + url);

			if ("the page cannot be found".equalsIgnoreCase(title)
					|| title.contains("is not available")
					|| url.contains("/error/")
					|| url.toLowerCase().contains("/errorpage/")) {
				Assert.fail("Site is down. [Title: " + title + ", URL:" + url
						+ "]");
			}
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			wait.until(WebDriverFactory.documentLoad);
			wait.until(WebDriverFactory.imagesLoad);
			wait.until(WebDriverFactory.framesLoad);
		}
		// Log.event("Page Load Wait: (Sync)",
		// StopWatch.elapsedTime(startTime));

	} // waitForPageLoad

	/**
	 * To get the test orientation
	 * 
	 * <p>
	 * if test run on sauce lab device return landscape or portrait or valid
	 * message, otherwise check local device execution and return landscape or
	 * portrait or valid message
	 * 
	 * @return dataToBeReturned - portrait or landscape or valid message
	 */
	public static String getTestOrientation() {
		String dataToBeReturned = null;
		boolean checkExecutionOnSauce = false;
		boolean checkDeviceExecution = false;
		checkExecutionOnSauce = (System.getProperty("SELENIUM_DRIVER") != null || System
				.getenv("SELENIUM_DRIVER") != null) ? true : false;

		if (checkExecutionOnSauce) {
			checkDeviceExecution = ((System
					.getProperty("runUserAgentDeviceTest") != null) && (System
					.getProperty("runUserAgentDeviceTest")
					.equalsIgnoreCase("true"))) ? true : false;
			if (checkDeviceExecution) {
				dataToBeReturned = (System.getProperty("deviceOrientation") != null) ? System
						.getProperty("deviceOrientation")
						: "no sauce run system variable: deviceOrientation ";
			} else {
				dataToBeReturned = "sauce browser test: no orientation";
			}
		} else {
			checkDeviceExecution = (configProperty
					.hasProperty("runUserAgentDeviceTest") && (configProperty
					.getProperty("runUserAgentDeviceTest")
					.equalsIgnoreCase("true"))) ? true : false;
			if (checkDeviceExecution) {
				dataToBeReturned = configProperty
						.hasProperty("deviceOrientation") ? configProperty
						.getProperty("deviceOrientation")
						: "no local run config variable: deviceOrientation ";
			} else {
				dataToBeReturned = "local browser test: no orientation";
			}
		}
		return dataToBeReturned;
	}

	/**
	 * To wait for the specific element on the page
	 * 
	 * @param driver
	 *            : Webdriver
	 * @param element
	 *            : Webelement to wait for
	 * @return boolean - return true if element is present else return false
	 */
	public static boolean waitForElement(WebDriver driver, WebElement element) {
		return waitForElement(driver, element, maxElementWait);
	}

	/**
	 * To wait for the specific element on the page
	 * 
	 * @param driver
	 *            : Webdriver
	 * @param element
	 *            : Webelement to wait for
	 * @param maxWait
	 *            : Max wait duration
	 * @return boolean - return true if element is present else return false
	 */
	public static boolean waitForElement(WebDriver driver, WebElement element,
			int maxWait) {
		boolean statusOfElementToBeReturned = false;
		// long startTime = StopWatch.startTime();
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions
					.visibilityOf(element));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
				// Log.event("Element is displayed:: " + element.toString());
			}
		} catch (Exception e) {
			statusOfElementToBeReturned = false;
			// Log.event("Unable to find a element after " +
			// StopWatch.elapsedTime(startTime) + " sec ==> " +
			// element.toString());
		}
		return statusOfElementToBeReturned;
	}

	public static WebDriver switchWindows(WebDriver driver,
			String windowToSwitch, String opt, String closeCurrentDriver)
			throws Exception {

		WebDriver currentWebDriver = driver;
		WebDriver assingedWebDriver = driver;
		boolean windowFound = false;
		ArrayList<String> multipleWindows = new ArrayList<String>(
				assingedWebDriver.getWindowHandles());

		for (int i = 0; i < multipleWindows.size(); i++) {

			assingedWebDriver.switchTo().window(multipleWindows.get(i));

			if (opt.equals("title")) {
				if (assingedWebDriver.getTitle().equals(windowToSwitch)) {
					windowFound = true;
					break;
				}
			} else if (opt.equals("url")) {
				if (assingedWebDriver.getCurrentUrl().contains(windowToSwitch)) {
					windowFound = true;
					break;
				}
			}// if

		}// for

		if (!windowFound)
			throw new Exception("Window: " + windowToSwitch + ", not found!!");
		else {
			if (closeCurrentDriver.equals("true"))
				currentWebDriver.close();
		}

		return assingedWebDriver;

	}// switchWindows

	/**
	 * To compare two array list values,then print unique list value and print
	 * missed list value
	 * 
	 * @param expectedElements
	 *            - expected element list
	 * @param actualElements
	 *            - actual element list
	 * @return statusToBeReturned - returns true if both the lists are equal,
	 *         else returns false
	 */
	public static boolean compareTwoList(List<String> expectedElements,
			List<String> actualElements) {
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

	public static boolean deleteFiles() {
		try {
			File f1 = new File(WebDriverFactory.downloadFilePath);
			if (f1.listFiles().length != 0) {
				Arrays.stream(f1.listFiles()).forEach(File::delete);
				if (f1.list().length == 0) {
					// Log.message("Folder is Empty");
					return true;
				} else {
					Log.fail("Folder is not empty");
					return false;
				}
			} else {
				// Log.message("Folder is Empty");
				return false;
			}
		} catch (Exception e) {
			// Log.message(e.fillInStackTrace().toString());
			return false;
		}

	}

	/**
	 * To compare two HashMap values,then print unique list value and print
	 * missed list value
	 * 
	 * @param expectedList
	 *            - expected element list
	 * @param actualList
	 *            - actual element list
	 * @return statusToBeReturned - returns true if both the lists are equal,
	 *         else returns false
	 */
	public static boolean compareTwoHashMap(
			HashMap<String, String> expectedList,
			HashMap<String, String> actualList) {
		List<String> missedkey = new ArrayList<String>();
		HashMap<String, String> missedvalue = new HashMap<String, String>();
		try {
			for (String k : expectedList.keySet()) {
				if (!(actualList.get(k).equalsIgnoreCase(expectedList.get(k)))) {
					missedvalue.put(k, actualList.get(k));
					return false;
				}
			}
			for (String y : actualList.keySet()) {
				if (!expectedList.containsKey(y)) {
					missedkey.add(y);
					return false;
				}
			}
		} catch (NullPointerException np) {
			return false;
		}
		return true;
	}

	public static boolean checkFile(String type) {
		File f1 = new File(WebDriverFactory.downloadFilePath);
		if (f1.list().length == 1) {
			File[] listOfFiles = f1.listFiles();
			if (listOfFiles[0].isFile()) {
				System.out.println("Saved File " + listOfFiles[0].getName());
				System.out.println("Saved File Location "
						+ listOfFiles[0].getPath());
				// if(listOfFiles[0].getName().split("\\.")[1].equalsIgnoreCase(type))
				// Log.pass("File type was verified - "+type);
				// else
				// Log.failsoft("Error while verifying the file type");
			}
			// Log.message("File was downloaded successfully");

			return true;
		}
		// Log.fail("Folder is not empty");
		return false;

	}

}
