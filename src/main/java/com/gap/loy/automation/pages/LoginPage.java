package com.gap.loy.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class LoginPage extends Basepage {

    @FindBy(xpath = "//button[@class='sitewide-9pwq9q']")
    private WebElement PopUP;

    @FindBy(id = "MyAccountContainer")
    public WebElement accountDropdown;

    @FindBy(xpath = "//*[@id='MyAccountContainer']/ul/li/a[text()='Sign In']")
    private WebElement SignInButton;

    @FindBy(xpath = "//input[@name='acc-verifyEmailAddress']")
    private WebElement emailTextField;

    @FindBy(id = "verify-account-email")
    public WebElement EnterEmail;

    @FindBy(css = ".loyalty-email-button, .loyalty-signInForm-button")
    public WebElement SignIN;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement VerifyPasswordPage;

    @FindBy(xpath = "//input[@type='password']")
    public WebElement EnterPassword;

    @FindBy(xpath = "//a[text()='Account']")
    private WebElement VerifyHomePage;

    public void POPUP() {
        try{ waitAndClick(PopUP); }
        catch(Exception e){// skip when no pop-up
             }
    }

    public void login() {
        waitAndClick(accountDropdown);
    }

    public void SignInButton() {
        waitAndClick(SignInButton);
    }

    public boolean VerifySignIn() {
        return isElementDisplayed(emailTextField);
    }

    public void EnterEmail(String email) throws InterruptedException {

       enterText(EnterEmail, email);
       waitAndClick(SignIN);
        Thread.sleep(4000);
        waitForSecOverlay();
        reLoginIfFailed(email);
    }

    public void reLoginIfFailed(String email) throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.cssSelector(".loyalty-email-button-refresh"));
        if (!elements.isEmpty() && isElementDisplayed(elements.get(0))) {
            (elements.get(0)).click();
            EnterEmail(email);
            waitAndClick(SignIN);
        }
    }


    public boolean VerifyPasswordPage() {
        return isElementDisplayed(VerifyPasswordPage);
    }

    public void EnterPassword(String Pwd) {
        enterText(EnterPassword, Pwd);
        waitAndClick(SignIN);
    }
    public boolean VerifyHomePage() {
        return isElementDisplayed(VerifyHomePage);
    }

}

