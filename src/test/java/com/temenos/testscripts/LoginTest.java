package com.temenos.testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.temenos.pages.HomePage;
import com.temenos.pages.LoginPage;
import com.temenos.support.DataProviderUtils;
import com.temenos.support.Log;
import com.temenos.support.WebDriverFactory;

public class LoginTest extends BaseTest {

	@Test(description = "Verify login functionality.", dataProvider = "TCIB_DP", dataProviderClass = DataProviderUtils.class)
	public void verifyLogin(String browser) {
		Log.testCaseInfo("Verify login functionality.");

		WebDriver driver = null;
		LoginPage loginPage = null;
		HomePage homePage = null;

		try {
			driver = WebDriverFactory.get(browser);

			loginPage = new LoginPage(driver, url);
			loginPage.login(username, password);

			homePage = new HomePage(driver).get(); // first time driver will be
													// passed
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Log.testCaseResult();
			driver.quit();
			Log.endTestCase();
		}
	}
}
