package com.temenos.support;

import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


public class DataProviderUtils {
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();

	@DataProvider(parallel = true, name="TCIB_DP")
	public static Iterator <Object[]> parallelTestDataProvider(ITestContext context) throws IOException {
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		List<String> browsers = null;
		List<String> platforms = null;
		List<String> browserVersions = null;
		String driverInitilizeInfo = null;
		String browser = null;
		String platform = null;
		String browserVersion = null;
		Iterator<String> browsersIt = null;
		Iterator<String> browserVersionsIt = null;
		Iterator<String> platformsIt = null;

		// From local to sauce lab for browser test
		if (configProperty.hasProperty("runSauceLabFromLocal")
				&& configProperty.getProperty("runSauceLabFromLocal").equalsIgnoreCase("true")) {
			browser = configProperty.hasProperty("browserName") ? configProperty.getProperty("browserName") : null;
			browserVersion = configProperty.hasProperty("browserVersion") ? configProperty.getProperty("browserVersion")
					: null;
			platform = configProperty.hasProperty("platform") ? configProperty.getProperty("platform") : null;

			browsers = Arrays.asList(browser.split("\\|"));
			browserVersions = Arrays.asList(browserVersion.split("\\|"));
			platforms = Arrays.asList(platform.split("\\|"));

			browsersIt = browsers.iterator();
			browserVersionsIt = browserVersions.iterator();
			platformsIt = platforms.iterator();

			// handling parallel browser test inputs
			while (browsersIt.hasNext() && browserVersionsIt.hasNext() && platformsIt.hasNext()) {
				browser = browsersIt.next();
				browserVersion = browserVersionsIt.next();
				platform = platformsIt.next();
				driverInitilizeInfo = browser + "&" + browserVersion + "&" + platform;
				dataToBeReturned.add(new Object[] { driverInitilizeInfo });
			}
		} else {
			// local to local test execution and also handling parallel support
			driverInitilizeInfo = configProperty.getBrowserName() + "_" + configProperty.getPlatform();
			dataToBeReturned.add(new Object[] { driverInitilizeInfo });
		}
		return dataToBeReturned.iterator();
	}
}