package com.temenos.testscripts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.temenos.support.EmailReport;
import com.temenos.support.EnvironmentPropertiesReader;

@Listeners(EmailReport.class)
public class BaseTest {
    protected static ExtentReports extent;
    EnvironmentPropertiesReader properties = EnvironmentPropertiesReader.getInstance();
    public String username;
    public String password;
    public String url;

    @BeforeClass(alwaysRun = true)
    public void beforeSuite() {
        username = properties.getUsername();
        password = properties.getPassword();
        url = properties.getURL();
        System.getProperties().put("hubHost", properties.getDeviceHost());
        System.getProperties().put("devicePort", properties.getDevicePort());
    }

    public boolean verifyPageElementsAreDisplayed(List<String> expectedTabs, Object obj, WebDriver driver) {
        List<String> missingElements = new ArrayList<String>();
        for (String tab : expectedTabs) {
            try {
                Field f = obj.getClass().getDeclaredField(tab);
                f.setAccessible(true);
                if (!((WebElement) f.get(obj)).isDisplayed()) {
                    missingElements.add(tab);
                }
            } catch (Exception e) {
                e.printStackTrace();
                missingElements.add(tab);
            }
        }
        
        return missingElements.isEmpty();

//        Log.softAssertThat(missingElements.isEmpty(), expectedTabs + " are displayed correctly.",
//                missingElements + " are not displayed.");
    }

    public boolean verifyPageElementsAreNotDisplayed(List<String> expectedElements, Object obj, WebDriver driver) {
        List<String> addedElements = new ArrayList<String>();
        for (String element : expectedElements) {
            try {
                Field f = obj.getClass().getDeclaredField(element);
                f.setAccessible(true);
                if (((WebElement) f.get(obj)).isDisplayed()) {
                    addedElements.add(element);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return addedElements.isEmpty();
//        Log.softAssertThat(addedElements.isEmpty(), expectedElements + " are not displayed.",
//                addedElements + " are displayed.");
    }
}
