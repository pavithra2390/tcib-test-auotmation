package com.temenos.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.temenos.support.BrowserActions;
import com.temenos.support.EnvironmentPropertiesReader;
import com.temenos.support.Log;
import com.temenos.support.Utils;
import com.temenos.support.WaitUtil;

/**
 * The Class LoginPage - This class is used for logging into the application.
 */
public class LoginPage {

	/**
	 * The driver.
	 */
	private WebDriver driver;

	@FindBy(css = "sample_elem")
	private WebElement lblTotalBal;

	@FindBy(css = "sample_username")
	private WebElement uName;

	String userName;

	/**
	 * Instantiates a new login page.
	 *
	 * @param driver
	 *            the driver
	 */
	public LoginPage(WebDriver driver, String url) throws Exception {
		this.driver = driver;
		handleAuthentication(url);
		PageFactory.initElements(driver, this);
	}

	public LoginPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void login(String username, String password) throws Exception {
		(new LoginScreen1(driver)).get().enterUsernameAndClickNext(username);
		(new LoginScreen2(driver)).get().enterPasswordAndClickLogin(password);
	}

	private void handleAuthentication(String url) throws Exception {
		EnvironmentPropertiesReader properties = EnvironmentPropertiesReader
				.getInstance();
		Log.message("Launching the TEMENOS Application");
		if ("mac".equalsIgnoreCase(properties.getPlatform())) {
			String authUrl = "http://" + properties.getAuthUsername() + ":"
					+ properties.getAuthPassword() + "@"
					+ properties.getURL().replace("http://", "");
			driver.get(authUrl);
		} else if ("ie".equalsIgnoreCase(properties.getBrowserName())
				|| "iexplorer".equalsIgnoreCase(properties.getBrowserName())) {
			driver.get(url);
			WaitUtil.waitForAlertToDisplay(driver, 60);
			driver.switchTo()
					.alert()
					.authenticateUsing(
							new UserAndPassword("aspiresys", "aspire@123"));
			driver.get(url);
		} else {
			String authUrl = "http://" + properties.getAuthUsername() + ":"
					+ properties.getAuthPassword() + "@"
					+ properties.getURL().replace("http://", "");
			driver.get(authUrl);
			// driver.get(url);
		}
	}

	public class LoginScreen1 extends LoadableComponent<LoginScreen1> {

		/**
		 * The driver.
		 */
		private WebDriver driver;

		private boolean isFailedFirstTime;

		@FindBy(css = "input#C2__USER_NAME")
		private WebElement txtUsername;

		@FindBy(css = "a#C2__BUT_21AA8B8279AB849B2246")
		private WebElement lnkNext;

		@FindBy(css = "input#C2__REMEMBER_ME_CHECKBOX_0")
		private WebElement checkRememberMe;

		@FindBy(css = "a#C2__HELP_USER_NAME")
		private WebElement lnkHelpUsername;

		@FindBy(css = "#helpContent span.tetris_helpText")
		private WebElement labelHelpContent;

		@FindBy(css = "a[title='close']")
		private WebElement lnkCloseHelp;

		public LoginScreen1(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		@Override
		protected void isLoaded() throws Error {
			if (!isFailedFirstTime
					&& !WaitUtil.waitForElement(driver, txtUsername)) {
				isFailedFirstTime = true;
				throw new Error();
			} else if (isFailedFirstTime) {
				Log.fail("Login was not successful!", driver);
			}
		}

		@Override
		protected void load() {
			WaitUtil.waitForPageLoad(driver);
		}

		public void enterUsernameAndClickNext(String username) throws Exception {
			userName = username;
			BrowserActions.typeOnTextFieldUsingJS(txtUsername, username,
					driver, "Username TextField");
			BrowserActions.clickOnButton(lnkNext, driver, "Next Button");
		}
	}

	public class LoginScreen2 extends LoadableComponent<LoginScreen2> {

		/**
		 * The driver.
		 */
		private WebDriver driver;

		private boolean isFailedFirstTime;

		@FindBy(css = "input#C2__QUE_21AA8B8279AB849B5250")
		private WebElement txtPassword;

		@FindBy(css = "input#C2__USER_NAME")
		private WebElement txtUsername;

		@FindBy(css = "a#C2__BUT_E08B195153315344131925")
		private WebElement lnkLogin;

		@FindBy(css = "a[oldtitle='Cancel']")
		private WebElement lnkCancel;

		@FindBy(css = ".tc-help-icon a")
		private WebElement lnkHelpPassword;

		@FindBy(css = "#helpContent span.tetris_helpText")
		private WebElement labelHelpContent;

		@FindBy(css = "a[title='close']")
		private WebElement lnkCloseHelp;

		@FindBy(css = "#C2__BUT_E08B195153315344131925 > span")
		private WebElement loginError;

		public LoginScreen2(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		@Override
		protected void isLoaded() throws Error {
			if (!isFailedFirstTime
					&& !WaitUtil.waitForElement(driver, txtPassword)) {
				isFailedFirstTime = true;
				throw new Error();
			} else if (isFailedFirstTime) {
				Log.fail("Login was not successful!", driver);
			}
		}

		@Override
		protected void load() {
			WaitUtil.waitForPageLoad(driver);
		}

		public void enterPasswordAndClickLogin(String password)
				throws Exception {
			BrowserActions.typeOnTextFieldUsingJSPwd(txtPassword, password,
					driver, "Password TextField");
			BrowserActions.clickOnButton(lnkLogin, driver, "Login Button");

			if (Utils.waitForElement(driver, txtUsername, 5)) {
				Log.fail("Login error : Invalid Username or Password");
			} else {
				Log.message("Logged in successfully using username : " + userName);
			}
		}
	}

	public String getTextfromTotalBal() throws Exception {
		WaitUtil.waitForElement(driver, lblTotalBal, 15);
		String TotalBal = BrowserActions.getText(driver, lblTotalBal,
				"Total Balance");
		return TotalBal;
	}

	public boolean checkPageLoaded() throws Exception {
		WaitUtil.sleep(5);
		return uName.isDisplayed();
	}

}
