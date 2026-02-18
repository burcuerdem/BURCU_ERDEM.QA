package com.insiderone.pages;

import com.insiderone.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Insider One ana sayfası: URL/başlık doğrulama ve "We're hiring" linki.
 */
public class HomePage extends BasePage {

    private static final By HEADING_UNSTOPPABLE = By.xpath(
            "//*[contains(text(), 'Be unstoppable') or contains(text(), 'unstoppable in customer engagement')]");
    private static final By LINK_WERE_HIRING = By.linkText("We're hiring");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.BASE_URL + "/");
        acceptCookieBannerIfPresent();
        return this;
    }

    public String getTitle() {
        return getPageTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Ana sayfada olduğumuzu doğrular: URL ve sayfa başlığı veya benzersiz metin.
     */
    public boolean isOnHomePage() {
        String url = getCurrentUrl();
        if (!url.startsWith(Config.BASE_URL) || url.length() > Config.BASE_URL.length() + 2) {
            return false;
        }
        String title = getTitle();
        if (title != null && title.contains("Insider One")) {
            return true;
        }
        try {
            WebElement heading = driver.findElement(HEADING_UNSTOPPABLE);
            return heading != null && heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickWereHiring() {
        click(LINK_WERE_HIRING);
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains("career")) {
                break;
            }
        }
    }

    public boolean isWereHiringDisplayed() {
        return isDisplayed(LINK_WERE_HIRING);
    }
}
