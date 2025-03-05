package com.loonycorn.learningselenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class DriverFactory {


    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
    }

    public static WebDriver initializeDriver(BrowserType browserType) {
        WebDriver driver = null;
        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().maximize();
                //driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Implicit wait. Explicit wait is written in NB.
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
        return driver;
    }
}

