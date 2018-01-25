package com.temenos.support;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Wrapper for Selenium WebDriver actions which will be performed on browser
 * <p>
 * Wrappers are provided with exception handling which throws Skip Exception on
 * occurrence of NoSuchElementException
 *
 * @author harish.subramani
 */
public class BrowserActions {

	/**
	 * Wrapper to type a text in browser text field
	 *
	 * @param txt
	 *            : WebElement of the Text Field
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void typeOnTextField(WebElement txt, String txtToType, WebDriver driver, String elementDescription)
			throws Exception {
		if (!WaitUtil.waitForElement(driver, txt))
			throw new Exception(elementDescription + " field not found in page!!");
		try {
			txt.clear();
			txt.click();
			txt.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");
		}
	}

	public static void typeWithoutClearTextField(WebElement txt, String txtToType, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, txt))
			throw new Exception(elementDescription + " field not found in page!!");

		try {
			txt.click();
			txt.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");
		}
	}

	/**
	 * Wrapper to type a text in browser text field
	 *
	 * @param txt
	 *            : String Input (CSS Locator)
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void typeOnTextField(String txt, String txtToType, WebDriver driver, String elementDescription)
			throws Exception {
		WebElement element = driver.findElement(By.cssSelector(txt));
		if (!WaitUtil.waitForElement(driver, element))
			throw new Exception(elementDescription + " field not found in page!!");

		try {
			element.clear();
			element.click();
			element.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");
		}
	}

	/**
	 * Wrapper to type a text in browser text field using Javascript
	 *
	 * @param txt
	 *            : String Input (CSS Locator)
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void typeOnTextFieldUsingJS(WebElement txt, String txtToType, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, txt))
			throw new Exception(elementDescription + " field not found in page!!");
		try {
			txt.clear();
			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", txt, txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");
		}
	}

	public static void typeOnTextFieldUsingJSPwd(WebElement txt, String txtToType, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, txt))
			throw new Exception(elementDescription + " field not found in page!!");

		try {
			txt.clear();
			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", txt, txtToType);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " field not found in page!!");
		}
	}

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 *
	 * @param btn
	 *            : WebElement of the Button Field
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception
	 */
	public static void clickOnButton(WebElement btn, WebDriver driver, String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, btn))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			try {
				btn.click();
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", btn);
			}
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}

	}

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 *
	 * @param btn
	 *            : String Input (CSS Locator) [of the Button Field]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void clickOnButton(String btn, WebDriver driver, String elementDescription) throws Exception {
		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!WaitUtil.waitForElement(driver, element))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			element.click();
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	public static void clickOnButtonJS(WebElement btn, WebDriver driver, String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, btn))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", btn);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	/**
	 * Wrapper to check element visibility
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param cssSelectorForWebElement
	 *            : CSS Selector of the WebElement which visibility to check in
	 *            String format
	 * @return: Boolean form - True if element visible/ False if element not visible
	 */
	public static boolean elementDisplayed(WebDriver driver, String cssSelectorForWebElement) {
		boolean displayed = false;

		try {
			displayed = driver.findElement(By.cssSelector(cssSelectorForWebElement)).isDisplayed();
		} catch (NoSuchElementException e) {
		}
		return displayed;
	}

	/**
	 * Wrapper to check element visibility
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param element
	 *            : WebElement Instance
	 * @return: Boolean form - True if element visible/ False if element not visible
	 */
	public static boolean elementDisplayed(WebDriver driver, WebElement element) {
		boolean displayed = false;

		try {
			displayed = element.isDisplayed();
		} catch (NoSuchElementException e) {
		}
		return displayed;
	}

	/**
	 * Wrapper to get a text from the provided WebElement
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @return: String - text from web element
	 */
	public static String getText(WebDriver driver, WebElement fromWhichTxtShldExtract, String elementDescription)
			throws Exception {
		String textFromHTMLAttribute = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			textFromHTMLAttribute = wait.until(ExpectedConditions.visibilityOf(fromWhichTxtShldExtract)).getText()
					.trim();
			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute("textContent").trim();
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
		return textFromHTMLAttribute;
	}

	/*
	 * public static String getAlert(WebDriver driver, WebElement
	 * fromWhichTxtShldExtract, String elementDescription) { String
	 * textFromHTMLAttribute = "";
	 * 
	 * try { WebDriverWait wait = new WebDriverWait(driver, 60);
	 * textFromHTMLAttribute = fromWhichTxtShldExtract.getText().trim();
	 * 
	 * if (textFromHTMLAttribute.isEmpty()) textFromHTMLAttribute =
	 * fromWhichTxtShldExtract.getAttribute( "textContent").trim(); } catch
	 * (NoSuchElementException e) { throw new SkipException(elementDescription +
	 * " not found in page!!"); } return textFromHTMLAttribute; }
	 */

	/**
	 * Wrapper to get a text from the provided WebElement
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in
	 *            String format]
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @return: String - text from web element
	 */
	public static String getText(WebDriver driver, String fromWhichTxtShldExtract, String elementDescription)
			throws Exception {
		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));
		try {
			textFromHTMLAttribute = element.getText();
			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = element.getAttribute("textContent");
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
		return textFromHTMLAttribute;
	}

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style,
	 *            class, value,..."
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @return: String - text from web element
	 */
	public static String getTextFromAttribute(WebDriver driver, WebElement fromWhichTxtShldExtract,
			String attributeName, String elementDescription) throws Exception {
		String textFromHTMLAttribute = "";

		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
		return textFromHTMLAttribute;
	}

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 *
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in
	 *            String format]
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style,
	 *            class, value,..."
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @return: String - text from web element
	 */
	public static String getTextFromAttribute(WebDriver driver, String fromWhichTxtShldExtract, String attributeName,
			String elementDescription) throws Exception {
		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));
		try {
			textFromHTMLAttribute = element.getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
		return textFromHTMLAttribute;
	}

	/**
	 * Wrapper to select option from combobox in browser
	 *
	 * @param btn
	 *            : WebElement of the combobox Field
	 * @param optToSelect
	 *            : option to select from combobox
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void selectFromComboBox(WebElement btn, String optToSelect, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, btn, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(btn);
			selectBox.selectByValue(optToSelect);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	/**
	 * Wrapper to select option from combobox in browser
	 *
	 * @param btn
	 *            : String Input (CSS Locator) [of the ComboBox Field]
	 * @param optToSelect
	 *            : option to select from combobox
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void selectFromComboBox(String btn, String optToSelect, WebDriver driver, String elementDescription)
			throws Exception {
		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!WaitUtil.waitForElement(driver, element, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(element);
			selectBox.selectByValue(optToSelect);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	/**
	 * Wrapper to select option from DropDown in browser
	 *
	 * @param btn
	 *            : WebElement of the DropDown Field
	 * @param optToSelect
	 *            : option to select from DropDown
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 * @throws Exception
	 */
	public static void selectFromDropDown(WebElement btn, String optToSelect, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, btn, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(btn);
			selectBox.selectByVisibleText(optToSelect);
			WaitUtil.sleep(1);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	/**
	 * Wrapper to select option from DropDown in browser
	 *
	 * @param btn
	 *            : WebElement of the DropDown Field
	 * @param optToSelect
	 *            : option to select from DropDown
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void selectFromDropDownValue(WebElement btn, String optToSelect, WebDriver driver,
			String elementDescription) throws Exception {
		if (!WaitUtil.waitForElement(driver, btn, 1))
			throw new Exception(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(btn);
			// selectBox.selectByVisibleText(optToSelect);
			selectBox.selectByValue(optToSelect);
			WaitUtil.sleep(1);
		} catch (NoSuchElementException e) {
			throw new Exception(elementDescription + " not found in page!!");
		}
	}

	public static void scrollPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,500)");
	}
}
