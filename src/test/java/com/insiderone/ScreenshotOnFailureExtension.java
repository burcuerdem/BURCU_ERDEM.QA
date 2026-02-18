package com.insiderone;

import com.insiderone.utils.ScreenshotUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

/**
 * Test başarısız olduğunda ekran görüntüsü alır.
 */
public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();
            if (driver != null) {
                String name = context.getDisplayName();
                if (context.getTestMethod().isPresent()) {
                    name = context.getTestMethod().get().getName();
                }
                String path = ScreenshotUtils.capture(driver, name);
                if (path != null) {
                    System.err.println("[Screenshot on failure] " + path);
                }
            }
        }
    }
}
