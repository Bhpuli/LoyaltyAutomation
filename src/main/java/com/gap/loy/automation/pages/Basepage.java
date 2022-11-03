package com.gap.loy.automation.pages;

import com.gap.loy.automation.utils.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class Basepage {

    WebDriver driver;

    private final FluentWait<WebDriver> wait = new FluentWait<>(TestBase.getWebDriver())
            .withTimeout(Duration.ofSeconds(40))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoring(Exception.class);

    public Basepage() {
        driver = TestBase.getWebDriver();
        PageFactory.initElements(driver, this);
    }

    public void waitAndClick(WebElement element) {
        waitAndClick(element, false);
    }

    public void waitAndClick(WebElement element, boolean scrollIntoView) {
        wait.until(webDriver -> {
            if (scrollIntoView) {
                scrollTo(element);
                sleepFor(1000L);
            }
            element.click();
            return webDriver;
        });
    }

    public void enterText(WebElement element, String text) {
        wait.until(webDriver -> {
            element.sendKeys(text);
            return webDriver;
        });
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return wait.until(webDriver ->
                element.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public void sleepFor(Long millSec) {
        try {
            Thread.sleep(millSec);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleepFor(500L);
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
        sleepFor(500L);
    }

    public void waitForSecOverlay() {
        try {
            wait.until(webDriver -> {
                List<WebElement> elements = TestBase.getWebDriver().findElements(By.id("sec-overlay"));
                if(!elements.isEmpty()) {
                    return !elements.get(0).isDisplayed();
                } else {
                    return true;
                }
            });
        } catch (Exception e) {
            System.out.println("overlay check exception, " + e.getMessage());
        }
    }

}
