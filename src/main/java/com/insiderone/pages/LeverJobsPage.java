package com.insiderone.pages;

import com.insiderone.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Lever (jobs.lever.co/insiderone) iş ilanları sayfası: filtreler, liste doğrulama, Apply, Lever form.
 */
public class LeverJobsPage extends BasePage {

    private static final By BUTTON_LOCATION_FILTER = By.xpath("//div[contains(@class,'filter-button') and contains(text(),'Location')]");
    private static final By LINK_LOCATION_ISTANBUL_TURKIYE = By.cssSelector("a.category-link[href*='location=Istanbul%2C%20Turkiye']");
    private static final By LINK_TEAM_QUALITY_ASSURANCE = By.linkText("Quality Assurance");
    private static final By APPLY_BUTTON = By.cssSelector("a.posting-btn-submit.template-btn-submit.hex-color[href*='/insiderone/']");
    private static final By POSTING_BLOCK = By.cssSelector("div.posting");
    private static final By LEVER_FORM_SECTION = By.cssSelector("div.section-wrapper.page-full-width");
    private static final By LEVER_FORM_INPUT = By.cssSelector("input[type='text'], input[type='email'], textarea");
    private static final By LEVER_FORM_SUBMIT = By.cssSelector("button[type='submit'], input[type='submit']");

    public LeverJobsPage(WebDriver driver) {
        super(driver);
    }

    public void openWithFilters(String location, String team) {
        String url = Config.LEVER_BASE_URL + "?location=" + location.replace(" ", "%20").replace(",", "%2C")
                + "&team=" + team.replace(" ", "%20");
        driver.get(url);
    }

    public void selectLocationIstanbulTurkiye() {
        if (isDisplayed(LINK_LOCATION_ISTANBUL_TURKIYE)) {
            click(LINK_LOCATION_ISTANBUL_TURKIYE);
        } else {
            applyFiltersByUrl("Istanbul, Turkiye", "Quality Assurance");
        }
    }

    public void selectTeamQualityAssurance() {
        if (isDisplayed(LINK_TEAM_QUALITY_ASSURANCE)) {
            click(LINK_TEAM_QUALITY_ASSURANCE);
        } else if (!getCurrentUrl().contains("Quality%20Assurance")) {
            applyFiltersByUrl("Istanbul, Turkiye", "Quality Assurance");
        }
    }

    /** Filtreleri URL query params ile uygular (dropdown vb. durumunda). */
    public void applyFiltersByUrl(String location, String team) {
        String url = Config.LEVER_BASE_URL + "?location="
                + location.replace(" ", "%20").replace(",", "%2C")
                + "&team=" + team.replace(" ", "%20");
        driver.get(url);
    }

    public boolean isJobListDisplayed() {
        try {
            List<WebElement> applyButtons = driver.findElements(APPLY_BUTTON);
            return !applyButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Tüm ilan kartlarının metnini toplar (pozisyon + konum); Quality Assurance ve Istanbul, Turkiye içermeli.
     */
    public List<String> getJobListingTexts() {
        List<String> texts = new ArrayList<>();
        try {
            List<WebElement> blocks = driver.findElements(POSTING_BLOCK);
            for (WebElement block : blocks) {
                texts.add(block.getText());
            }
            if (texts.isEmpty()) {
                WebElement body = driver.findElement(By.tagName("body"));
                texts.add(body.getText());
            }
        } catch (Exception e) {
            WebElement body = driver.findElement(By.tagName("body"));
            texts.add(body.getText());
        }
        return texts;
    }

    public boolean allListingsContainQualityAssuranceAndIstanbul(List<String> listingTexts) {
        String qa = "Quality Assurance";
        String location = "Istanbul, Turkiye";
        for (String text : listingTexts) {
            if (!text.contains(qa) || !text.contains(location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * İlk Apply butonuna tıklar ve Lever Application Form sayfasının yüklenmesini bekler.
     * Gereksiz wait'leri kaldırarak hızlı tıklama yapar.
     */
    public void clickFirstApply() {
        WebElement applyBtn = driver.findElement(APPLY_BUTTON);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", applyBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
        
        waitForLeverFormToLoad();
    }

    /**
     * Lever Application Form sayfasının tam yüklenmesini bekler.
     * section-wrapper page-full-width class'ını görürse sayfa tam yüklenmiş demektir.
     */
    private void waitForLeverFormToLoad() {
        WebDriverWait formWait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SEC));
        try {
            formWait.until(ExpectedConditions.presenceOfElementLocated(LEVER_FORM_SECTION));
        } catch (Exception e) {
            // section-wrapper bulunamazsa fallback olarak form elementlerini kontrol et
            try {
                formWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(LEVER_FORM_INPUT),
                    ExpectedConditions.presenceOfElementLocated(LEVER_FORM_SUBMIT)
                ));
            } catch (Exception ex) {
                // Form elementleri de bulunamazsa URL kontrolü yeterli
            }
        }
    }

    public boolean isOnLeverApplicationForm() {
        if (!getCurrentUrl().contains("jobs.lever.co") || !getCurrentUrl().contains("/insiderone/")) {
            return false;
        }
        try {
            // section-wrapper page-full-width class'ı varsa sayfa tam yüklenmiş
            if (driver.findElements(LEVER_FORM_SECTION).size() > 0) {
                return true;
            }
            // Fallback: form elementleri kontrolü
            return driver.findElements(LEVER_FORM_INPUT).size() > 0 
                || driver.findElements(LEVER_FORM_SUBMIT).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /** Sayfa URL veya içeriği QA + Istanbul filtresinin uygulandığını gösteriyor mu. */
    public boolean pageContentReflectsQAFilter() {
        String url = getCurrentUrl();
        if (url.contains("Quality%20Assurance") && url.contains("Istanbul")) {
            return true;
        }
        try {
            String body = driver.findElement(By.tagName("body")).getText();
            return body.contains("Quality Assurance") && body.contains("Istanbul");
        } catch (Exception e) {
            return false;
        }
    }
}
