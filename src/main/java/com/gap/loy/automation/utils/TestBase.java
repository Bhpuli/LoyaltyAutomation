package com.gap.loy.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Timestamp;

public class TestBase {

    private static WebDriver driver;
    private static TestData testData;

    public static void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static WebDriver getWebDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }
    public static TestData getTestData() {
        if (testData == null) {
            testData = new TestData();
        }
        return testData;
    }

    public static void destroy() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

    }
    public static void log(String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Allure.addAttachment(timestamp + " : " + message, "");
    }

//    public static WebDriver close() {
//        if (driver != null) {
//            driver.close();
//        }
//        return driver;
//    }

}
