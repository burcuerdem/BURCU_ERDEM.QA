package com.insiderone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Açık pozisyonlar sayfası: Software Development bloğu ve "Open Positions" linki.
 */
public class OpenPositionsPage extends BasePage {

    private static final By CONTAINER_CARDS = By.cssSelector("div.insiderone-icon-cards-container");
    private static final By CARD_SOFTWARE_DEVELOPMENT = By.cssSelector("div[data-department='Software Development']");
    private static final By LINK_OPEN_POSITIONS_SOFTWARE = By.cssSelector(
            "div[data-department='Software Development'] a.insiderone-icon-cards-grid-item-btn");

    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnOpenPositionsPage() {
        return getCurrentUrl().contains("open-positions") || getCurrentUrl().contains("jobs.lever.co");
    }

    /**
     * Software Development bölümü altındaki "X Open Positions" linkine tıklar.
     * Önce insiderone-icon-cards-container elementine kaydırır, sonra tıklar.
     */
    public void clickSoftwareDevelopmentOpenPositions() {
        WebElement container = find(CONTAINER_CARDS);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", container);
        click(LINK_OPEN_POSITIONS_SOFTWARE);
    }

    public boolean isSoftwareDevelopmentOpenPositionsLinkDisplayed() {
        return isDisplayed(LINK_OPEN_POSITIONS_SOFTWARE);
    }
}
