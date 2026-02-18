package com.insiderone.utils;

import com.insiderone.config.Config;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Test başarısız olduğunda ekran görüntüsü almak için yardımcı sınıf.
 */
public final class ScreenshotUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {}

    /**
     * Ekran görüntüsü alır ve screenshots/ klasörüne test adı ve timestamp ile kaydeder.
     *
     * @param driver   WebDriver
     * @param testName Test metod adı (dosya adı için)
     * @return Kaydedilen dosyanın path'i veya null
     */
    public static String capture(WebDriver driver, String testName) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            return null;
        }
        try {
            Path dir = Paths.get(Config.SCREENSHOT_DIR);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            String safeName = testName != null ? testName.replaceAll("[^a-zA-Z0-9_-]", "_") : "test";
            String fileName = safeName + "_" + FORMATTER.format(LocalDateTime.now()) + ".png";
            Path file = dir.resolve(fileName);
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(file, bytes);
            return file.toAbsolutePath().toString();
        } catch (IOException e) {
            return null;
        }
    }
}
