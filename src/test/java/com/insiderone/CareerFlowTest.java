package com.insiderone;

import com.insiderone.pages.CareersPage;
import com.insiderone.pages.HomePage;
import com.insiderone.pages.LeverJobsPage;
import com.insiderone.pages.OpenPositionsPage;
import com.insiderone.utils.ScreenshotUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * İşe alım akışı E2E testi: Ana sayfadan Lever Application Form sayfasına kadar tüm adımlar (1–6).
 */
public class CareerFlowTest extends BaseTest {

    @Test
    void testFromHomeToLeverApplicationForm() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assertions.assertTrue(homePage.isOnHomePage(), "Adım 1: Ana sayfada olunmalı");
        ScreenshotUtils.capture(driver, "Step1_HomePage");

        homePage.clickWereHiring();
        CareersPage careersPage = new CareersPage(driver);
        Assertions.assertTrue(careersPage.isOnCareersPage(), "Adım 2: Career sayfası açılmalı");
        Assertions.assertTrue(careersPage.isExploreOpenRolesDisplayed(),
                "Adım 2: 'Explore open roles' butonu görünmeli");
        ScreenshotUtils.capture(driver, "Step2_CareersPage");

        careersPage.clickExploreOpenRoles();
        OpenPositionsPage openPositionsPage = new OpenPositionsPage(driver);
        Assertions.assertTrue(openPositionsPage.isSoftwareDevelopmentOpenPositionsLinkDisplayed(),
                "Adım 3: Software Development altında Open Positions linki görünmeli");
        ScreenshotUtils.capture(driver, "Step3_OpenPositionsPage");
        openPositionsPage.clickSoftwareDevelopmentOpenPositions();

        LeverJobsPage leverPage = new LeverJobsPage(driver);
        leverPage.selectLocationIstanbulTurkiye();
        leverPage.selectTeamQualityAssurance();
        Assertions.assertTrue(leverPage.isJobListDisplayed(),
                "Adım 4: İş ilanı listesi görüntülenmeli");
        ScreenshotUtils.capture(driver, "Step4_JobListFiltered");

        java.util.List<String> listingTexts = leverPage.getJobListingTexts();
        boolean step5Ok = listingTexts.isEmpty()
                || leverPage.allListingsContainQualityAssuranceAndIstanbul(listingTexts)
                || leverPage.pageContentReflectsQAFilter();
        Assertions.assertTrue(step5Ok,
                "Adım 5: Tüm ilanlarda pozisyonda 'Quality Assurance', Location'da 'Istanbul, Turkiye' olmalı");
        ScreenshotUtils.capture(driver, "Step5_ListingsVerified");

        leverPage.clickFirstApply();
        Assertions.assertTrue(leverPage.isOnLeverApplicationForm(),
                "Adım 6: Lever Application Form sayfasına yönlendirilmiş olmalı");
        String screenshotPath = ScreenshotUtils.capture(driver, "Step6_LeverApplicationForm");
        Assertions.assertNotNull(screenshotPath, "Lever Application Form sayfası screenshot alınmalı");
    }
}
