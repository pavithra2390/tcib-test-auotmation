package com.temenos.support;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WaitUtil {
    private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
    private static int maxElementWait = Integer.parseInt(configProperty.getProperty("maxElementWait"));
 /*   private static int maxShortElementWait = Integer.parseInt(configProperty.getProperty("maxShortElementWait"));*/
    

    public static boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitForElementByName(WebDriver driver, String element, int maxWait) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, maxWait);
        try {
            WebElement waitElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
            }
        } catch (Exception ex) {
            statusOfElementToBeReturned = false;
        }
        return statusOfElementToBeReturned;
    }

    public static boolean waitForElementByClassName(WebDriver driver, String element, int maxWait) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, maxWait);

        try {
            WebElement waitElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
            }
        } catch (Exception ex) {
            statusOfElementToBeReturned = false;
        }
        return statusOfElementToBeReturned;
    }

    public static boolean waitForElementById(WebDriver driver, String element, int maxWait) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, maxWait);
        try {
            WebElement waitElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
            }
        } catch (Exception ex) {
            statusOfElementToBeReturned = false;
        }
        return statusOfElementToBeReturned;
    }


    /**
     * To wait for the specific element on the page
     *
     * @param driver  -
     * @param element - mobelement to wait for to appear
     * @return boolean - return true if element is present else return false
     */
    public static boolean waitForElement(WebDriver driver, WebElement element) {
        return waitForElement(driver, element, maxElementWait);
    }

    /**
     * To wait for the specific element on the page
     *
     * @param driver  -
     * @param element - mobelement to wait for to appear
     * @param maxWait - how long to wait for
     * @return boolean - return true if element is present else return false
     */

    public static boolean waitForElement(WebDriver driver, WebElement element, int maxWait) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, maxWait);
        try {
            WebElement waitElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
                //Log.event("Element is displayed: " +  element.toString());
            }
        } catch (Exception ex) {
            statusOfElementToBeReturned = false;
        }
        return statusOfElementToBeReturned;
    }

    public static boolean waitForElementToVanish(WebElement element, int maxWait) {
        try {
            int i = 0;
            while (element.isDisplayed() && i < 3) {
                Thread.sleep(maxWait);
                i++;
            }
            return !element.isDisplayed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sleep(int waitTimeInSec) {
        try {
            Thread.sleep(waitTimeInSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForAlertToDisplay(WebDriver driver, int waitTimeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeInSec);
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * waitForPageLoad waits for the page load with default page load wait time
     *
     * @param driver : Webdriver
     */
    public static void waitForPageLoad(final WebDriver driver) {
        waitForPageLoad(driver, WebDriverFactory.maxPageLoadWait);
    }

    /**
     * waitForPageLoad waits for the page load with custom page load wait time
     *
     * @param driver  : Webdriver
     * @param maxWait : Max wait duration
     */
    public static void waitForPageLoad(final WebDriver driver, int maxWait) {
        FluentWait<WebDriver> wait = new WebDriverWait(driver, maxWait).pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class).withMessage("Page Load Timed Out");
        try {
            if (configProperty.getProperty("documentLoad").equalsIgnoreCase("true"))
                wait.until(WebDriverFactory.documentLoad);

            if (configProperty.getProperty("imageLoad").equalsIgnoreCase("true"))
                wait.until(WebDriverFactory.imagesLoad);

            if (configProperty.getProperty("framesLoad").equalsIgnoreCase("true"))
                wait.until(WebDriverFactory.framesLoad);

            String title = driver.getTitle().toLowerCase();
            String url = driver.getCurrentUrl().toLowerCase();
            Log.event("Page URL:: " + url);

            if ("the page cannot be found".equalsIgnoreCase(title) || title.contains("is not available") || url.contains("/error/") || url.toLowerCase().contains("/errorpage/")) {
                Assert.fail("Site is down. [Title: " + title + ", URL:" + url + "]");
            }
        } catch (TimeoutException e) {
            driver.navigate().refresh();
            wait.until(WebDriverFactory.documentLoad);
            wait.until(WebDriverFactory.imagesLoad);
            wait.until(WebDriverFactory.framesLoad);
        }
    }

    public static boolean waitForElementToAppear(By locator, WebDriver driver, long timeToWait) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeToWait);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitForElementToDisappear(By locator, WebDriver driver, long timeToWait) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeToWait);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static void waitForSpinnerToComplete(WebDriver driver) {
        waitForSpinner(driver);
        WaitUtil.sleep(5);
        waitForSpinner(driver);
    }

    private static void waitForSpinner(WebDriver driver) {
        By screenMask = By.cssSelector(".tc-screenMask");
        By progressBar = By.cssSelector("#WRAPPER_C1__TXT_6DD52E1BC28297B080808");

        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).ignoring(NoSuchElementException.class).withTimeout(maxElementWait, TimeUnit.SECONDS);
            wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver t) {
                    if (!t.findElement(screenMask).getAttribute("style").contains("block") &&
                            !t.findElement(progressBar).getAttribute("style").contains("block")) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {

        }
    }
    
 /*   public static boolean waitForElementLoginError( WebDriver driver) {
        try {
        	 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).ignoring(NoSuchElementException.class).withTimeout(maxShortElementWait, TimeUnit.SECONDS);
             wait.until(new Function<WebDriver, Boolean>() {
                 @Override
                 public Boolean apply(WebDriver t) {
                	
                     if (t.findElement(By.cssSelector("div#growls div div.growl-message")).getText().contains("Invalid Username or Password")) { //getAttribute("class").contains("growl-icon")
                    	
                         return true;
                     }else if (t.findElement(By.cssSelector("div#growls div div.growl-message")).getText().contains("Your last visit")) { //getAttribute("class").contains("growl-icon")
                    	
                         return false;
                     }
                     return false;
                 }
             });
             return true;
        } catch (Exception e) {
            return false;
        }

    }
*/
    }
