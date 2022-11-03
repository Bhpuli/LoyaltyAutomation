package com.gap.loy.automation.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class ValueCenterPage extends Basepage {

    @FindBy(xpath = "//a[text()='My Points and Rewards']")
    private WebElement MyPointsAndRewards;

    @FindBy(xpath = "//button[@id='tab-button-0']")
    private WebElement VerifyValueCenterPage;

    @FindBy(xpath = "//div[text()='Test Test']")
    private WebElement VerifyFirstAndLastNames;

    @FindBy(xpath = "//div[@class='member-tier-container css-tbccqg']")
    private WebElement VerifyTierName;

    @FindBy(xpath = "//span[text()='$497 in rewards']")
    private WebElement VerifyRewardAmount;

    @FindBy(xpath = "//div[@class=' css-ntucxo' and @data-id='tierprogress']")
    private WebElement TierProgressSection;

    @FindBy(xpath = "//p[text()='CORE' and @class='sds_sp_top_xs css-1rs3ga9']")
    private WebElement TierProgressName;

    @FindBy(xpath = "//button[contains(@class, 'tab-header') and text()='Track Points']")
    private WebElement TrackPoints;

    @FindBy(xpath = "//*[@id='trackpoints-table']//div[contains(@class,'activity_row')]")
    private List<WebElement> pointsActivity;

    @FindBy(xpath = "//*[@id='trackpoints-table']//div[contains(@class,'activity_row')][1]/div[last()]//span")
    private WebElement latestActivityPts;

    @FindBy(css = ".charity-selector button")
    private WebElement donationsDropdown;

    @FindBy(css = ".charity-selector ul li")
    private List<WebElement> donationOrganizations;

    @FindBy(css = ".amount-selector ul li")
    private List<WebElement> donationAmounts;

    @FindBy(css = ".donate-button button")
    private WebElement donateBtn;

    @FindBy(css = ".amount-selector button")
    private WebElement amountDropdown;

    @FindBy(xpath = "//div[@data-id='convertcash']//input")
    private WebElement redeemCode;

    @FindBy(xpath = "//div[@data-id='convertcash']//button")
    private WebElement redeemBtn;

    @FindBy(xpath = "//div[@data-id='convertcash']//span[@id='info-text']")
    private WebElement redeemMessage;

    @FindBy(xpath = "//div[contains(@class, 'css-13pmxen')]//p[@id='info-text']")
    private WebElement donationMessage;

    @FindBy(id = "MyAccountContainer")
    public WebElement accountDropdown;

    @FindBy(xpath = "//button[@class='sitewide-9pwq9q']")
    private WebElement POPUP;

    @FindBy(xpath = "//span[@class='sds_font-size--30 css-1kf14io']")
    private WebElement LoyaltyPoints;

    @FindBy(xpath = "//span[text()='($497 in rewards)' and@class='sds_font-size--16 css-3sbjsu']")
    private WebElement LoyaltyRewards;

    @FindBy(xpath = "//button[contains(@class, 'tab-header') and text()='Earn & Redeem']")
    private WebElement EarnRedeem;

    @FindBy(xpath = "//div[@class='section-container rewards-list css-dmworq']")
    private WebElement EarnRedeemSection;

    @FindBy(xpath = "//span[text()='$497']")
    private WebElement EarnRedeemRewards;

    @FindBy(xpath = "//div[text()='Points balance: 49,700 points']")
    private WebElement EarnRedeemPointsBalance;

    @FindBy(xpath = "//span[@class='sds_g-1-2 css-ty7lyr']")
    private WebElement TierPoints;

    public Long getLoyaltyPoints() {
        String points = LoyaltyPoints.getText();
        return Optional.ofNullable(points)
                .map(str -> str.replaceAll("\\D", ""))
                .filter(StringUtils::isNumeric).map(Long::parseLong).orElse(0L);
    }

    public String getLatestPointsInActivity() {
        if (pointsActivity.isEmpty()) {
            throw new NoSuchElementException("Points activity list not found");
        }
        sleepFor(2000L);
        String pts = latestActivityPts.getText();
        System.out.println("Latest Points In Activity: " + pts);
        return pts;
    }

    public String selectOrganization() {
        waitAndClick(donationsDropdown);
        sleepFor(3000L);
        if (donationOrganizations.isEmpty()) {
            throw new NoSuchElementException("Donation Organizations list empty");
        }
        WebElement element = donationOrganizations.get(new Random().nextInt(donationOrganizations.size()));
        String organization = element.getText();
        System.out.println("Selecting organization: " + organization);
        waitAndClick(element);
        return organization;
    }

    public String selectDonationAmount() {
        waitAndClick(amountDropdown);
        if (donationAmounts.isEmpty()) {
            throw new NoSuchElementException("Donation amount list empty");
        }
        WebElement element = donationAmounts.get(0);
        String optedPoints = element.getAttribute("aria-label");
        System.out.println("Selecting points: " + optedPoints);
        waitAndClick(element);
        return optedPoints;
    }

    public void clickDonate() {
        waitAndClick(donateBtn);
    }

    public boolean isDonationSuccess() {
        isElementDisplayed(donationMessage);
        String message = donationMessage.getText();
        System.out.println("Donation info :" + message);
        return message.toLowerCase().contains("donated");
    }

    public String applyPromoCode(String code) {
        enterText(redeemCode, code);
        waitAndClick(redeemBtn);
        if (isElementDisplayed(redeemMessage)) {
            return redeemMessage.getText();
        } else {
            System.out.println("Redeption message is not displayed");
            return StringUtils.EMPTY;
        }
    }


    public void POPUP() {
        try{ waitAndClick(POPUP); }
        catch(Exception e){// skip when no pop-up
        } }

    public boolean VerifyValueCenterPage() { return isElementDisplayed(VerifyValueCenterPage); }
    public boolean VerifyFirstAndLastNames(){
        return isElementDisplayed(VerifyFirstAndLastNames);
    }
    public boolean VerifyTierName(){
        return isElementDisplayed(VerifyTierName);
    }
    public boolean VerifyRewardAmount(){ return isElementDisplayed(VerifyRewardAmount); }
    public boolean TierProgressSection() { return isElementDisplayed(TierProgressSection); }
    public boolean TierProgressName() { return isElementDisplayed(TierProgressName); }
    public boolean LoyaltyPoints() {return isElementDisplayed(LoyaltyPoints); }
    public boolean LoyaltyRewards() {return isElementDisplayed(LoyaltyRewards); }
    public boolean EarnRedeemSection() {return isElementDisplayed(EarnRedeemSection); }
    public boolean EarnRedeemRewards() {return isElementDisplayed(EarnRedeemRewards); }
    public boolean EarnRedeemPointsBalance() {return isElementDisplayed(EarnRedeemPointsBalance); }
    public boolean TierPoints() {return isElementDisplayed(TierPoints);
    }

    public void MyPointsAndRewards() {
        waitAndClick(MyPointsAndRewards);
    }
    public void TrackPoints() {
        scrollToTop();
        waitAndClick(TrackPoints);
    }
    public void Dropdown() { waitAndClick(accountDropdown); }
    public void EarnRedeem() { waitAndClick(EarnRedeem); }


}
