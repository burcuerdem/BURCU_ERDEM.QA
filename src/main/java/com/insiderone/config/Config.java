package com.insiderone.config;

/**
 * Test konfigürasyonu: URL'ler, timeout'lar, screenshot dizini.
 */
public final class Config {

    public static final String BASE_URL = "https://insiderone.com";
    public static final String CAREERS_URL = BASE_URL + "/careers/";
    public static final String OPEN_POSITIONS_URL = BASE_URL + "/careers/open-positions/";
    public static final String LEVER_BASE_URL = "https://jobs.lever.co/insiderone";

    public static final int PAGE_LOAD_TIMEOUT_SEC = 30;
    public static final int IMPLICIT_WAIT_SEC = 10;
    public static final int EXPLICIT_WAIT_SEC = 15;

    public static final String SCREENSHOT_DIR = "screenshots";

    private Config() {}
}
