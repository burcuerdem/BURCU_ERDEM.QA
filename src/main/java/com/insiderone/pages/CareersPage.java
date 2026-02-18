package com.insiderone.pages;

import com.insiderone.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Career sayfası: doğrulama ve "Explore open roles" butonu.
 */
public class CareersPage extends BasePage {

    private static final By BUTTON_EXPLORE_OPEN_ROLES = By.cssSelector("a.inso-btn.inso-btn-s.inso-btn-primary");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnCareersPage() {
        if (!getCurrentUrl().contains("career")) {
            return false;
        }
        try {
            new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SEC))
                    .until(ExpectedConditions.visibilityOfElementLocated(BUTTON_EXPLORE_OPEN_ROLES));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExploreOpenRolesDisplayed() {
        return isDisplayed(BUTTON_EXPLORE_OPEN_ROLES);
    }

    public void clickExploreOpenRoles() {
        click(BUTTON_EXPLORE_OPEN_ROLES);
    }

    public void openCareersDirect() {
        driver.get(Config.CAREERS_URL);
    }
}
