package org.calculator.factory;

import com.microsoft.playwright.*;

import java.awt.*;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    public Page initBrowser(String browserName){
        System.out.println("Initializing Browser: "+browserName);
        playwright=Playwright.create();
        Boolean isHeadless = false;

        switch (browserName.toLowerCase()){
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            default:
                System.out.println("Invalid browser name passed:" + browserName);
                break;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize((int)screenSize.getWidth(),(int)screenSize.getHeight()));
        page = browserContext.newPage();
        page.navigate("https://www.google.com");
//        // Accept cookies if prompted (may vary by region)
//        try {
//            page.locator("button:has-text('Accept all')").click();
//        } catch (Exception ignored) {}

        return page;
    }
}
