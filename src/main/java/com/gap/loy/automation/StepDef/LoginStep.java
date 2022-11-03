package com.gap.loy.automation.StepDef;

import com.gap.loy.automation.pages.LoginPage;
import com.gap.loy.automation.utils.ConfigProperties;
import com.gap.loy.automation.utils.TestBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Optional;
import java.util.Properties;

public class LoginStep {
    LoginPage login= new LoginPage();
    ConfigProperties configProperties = new ConfigProperties();
    Properties properties;
    String userPassword;

    @Given("^user launched Gap site$")
    public void userLaunchedGapSite() throws InterruptedException {
        WebDriver webDriver = TestBase.getWebDriver();
        String stageURL = configProperties.getUrl();
        System.out.println("Navigating to: " + stageURL);
        TestBase.log("Navigating to: " + stageURL);
        webDriver.get(stageURL);
        webDriver.manage().deleteAllCookies();
        Thread.sleep(6000);
        login.POPUP();
        login.login();
        login.SignInButton();
        setCookie();
        TestBase.getWebDriver().navigate().refresh();
    }

    public void setCookie() {
        Cookie consentCookie = TestBase.getWebDriver().manage().getCookieNamed("CONSENTMGR");
        Optional.ofNullable(consentCookie).ifPresent(cookie -> {
            TestBase.getWebDriver().manage().deleteCookie(cookie);
            TestBase.getWebDriver().manage().addCookie(
                    new Cookie.Builder(cookie.getName(), "consent:false")
                            .domain(cookie.getDomain())
                            .expiresOn(cookie.getExpiry())
                            .path(cookie.getPath())
                            .isSecure(cookie.isSecure())
                            .build());
        });
    }



    @And("I login with {string} user {string} and {string}")
    public void iLoginWithUserAnd(String tier, String email, String pass) throws Exception {
       // TestBase.log("Login email: " + email);

        login.enterText(login.EnterEmail, email);
        login.waitAndClick(login.SignIN);

        Thread.sleep(4000);
        login.waitForSecOverlay();
        login.reLoginIfFailed(email);

        login.enterText(login.EnterPassword, pass);
        login.waitAndClick(login.SignIN);

    }


    @Then("I should be on the {string} page")
    public void iShouldBeOnThePage(String page) {
        switch (page) {
            case "login":
                Assert.assertTrue("not on sign in page", login.VerifySignIn());
                break;
            case "password":
                Assert.assertTrue("not on password page", login.VerifyPasswordPage());
                break;
            case "my account":
                Assert.assertTrue("not on My account page", login.VerifyHomePage());
                break;
            default:
                throw new RuntimeException("Unknown page or no handle for page: " + page);
        }
    }



}
