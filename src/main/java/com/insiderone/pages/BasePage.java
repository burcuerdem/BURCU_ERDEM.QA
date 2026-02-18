package com.insiderone.pages;

import com.insiderone.config.Config;
import com.insiderone.utils.ScreenshotUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Tüm sayfa nesneleri için temel sınıf: driver, wait, ortak metotlar ve screenshot.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SEC));
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement findClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        WebElement el = findClickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean currentUrlContains(String part) {
        return getCurrentUrl().contains(part);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Cookie banner çıktıysa "Accept All" ile kapatır. Banner yoksa veya 5 sn içinde görünmezse sessizce devam eder.
     */
    protected void acceptCookieBannerIfPresent() {
        By acceptAll = By.id("wt-cli-accept-all-btn");
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(acceptAll));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            // Banner çıkmadı veya zaten kapatıldı
        }
    }

    /**
     * Başarısızlık anında ekran görüntüsü alır.
     */
    public String takeScreenshotOnFailure(String testName) {
        return ScreenshotUtils.capture(driver, testName);
    }
}
