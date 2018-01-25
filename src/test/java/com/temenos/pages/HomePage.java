package com.temenos.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.temenos.support.BrowserActions;
import com.temenos.support.Log;
import com.temenos.support.WaitUtil;

public class HomePage extends LoadableComponent<HomePage> {

	private WebDriver driver;

	private boolean isFailedFirstTime;

	@FindBy(css = "sample_css")
	private WebElement sampleelement;
	
	private Headers headers;

	private Footers footers;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		headers = new Headers(driver);
		footers = new Footers(driver);
	}

	@Override
	protected void isLoaded() throws Error {
		WaitUtil.waitForSpinnerToComplete(driver);
		if (!isFailedFirstTime
				&& !WaitUtil.waitForElement(driver, sampleelement)) {
			isFailedFirstTime = true;
			throw new Error();
		} else if (isFailedFirstTime
				&& !WaitUtil.waitForElement(driver, sampleelement)) {
			Log.fail("Not navigated to Homepage!", driver);
		} else {
			Log.message("Navigated to Homepage!", driver);
		}
	}

	@Override
	protected void load() {
		WaitUtil.waitForSpinnerToComplete(driver);
	}

	public boolean verifyOpenHomePage() throws Exception {
		WaitUtil.waitForElement(driver, sampleelement, 15);
		if (sampleelement.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public String getAllAccountsTab() throws Exception {
		return BrowserActions.getText(driver, sampleelement,
				"All accounts Tab");
	}

	public Headers getHeaders() {
		return headers;
	}

	public Footers getFooters() {
		return footers;
	}
}
