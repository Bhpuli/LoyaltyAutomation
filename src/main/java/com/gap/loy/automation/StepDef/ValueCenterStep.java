package com.gap.loy.automation.StepDef;

import com.gap.loy.automation.pages.ValueCenterPage;
import com.gap.loy.automation.utils.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;

public class ValueCenterStep {
    ValueCenterPage ValueCenter = new ValueCenterPage();

    @And("^I click on My Points And Rewards$")
    public void iClickOnMyPointsAndRewards() throws InterruptedException {
        ValueCenter.Dropdown();
        ValueCenter.MyPointsAndRewards();
        ValueCenter.POPUP();
    }


    @And("verify loggedIn user {string}")
    public void verifyLoggedInUser(String page) throws InterruptedException {
        switch (page) {
            case "Value center page":
                Assert.assertTrue("not on ValueCenterPage", ValueCenter.VerifyValueCenterPage());
                Thread.sleep(2000);
                break;
            case "First and Last Names":
                Assert.assertTrue("First & Last names are not matched", ValueCenter.VerifyFirstAndLastNames());
                break;
            case "Tier Name":
                Assert.assertTrue("TierName is not Matched", ValueCenter.VerifyTierName());
                break;
            case "Rewards amount":
                Assert.assertTrue("RewardsAmount is not matched", ValueCenter.VerifyRewardAmount());
                break;
            default:
                throw new RuntimeException("Unknown page or no handle for page: " + page);
        }

    }

    @And("Verify tier progress {string}")
    public void verifyTierProgressSection(String page) throws InterruptedException {
        switch (page) {
        case "section":
                Assert.assertTrue("Tier progress is not Matched", ValueCenter.TierProgressSection());
                break;
            case "Name":
                Assert.assertTrue("Tier progress is not Matched", ValueCenter.TierProgressName());
                break;
            case "tierPoints":
                Assert.assertTrue("Tier progress is not Matched", ValueCenter.TierPoints());
                break;
            default:
                throw new RuntimeException("Unknown page or no handle for page: " + page );
        }

    }

    @Then("^I click on Track Points section$")
    public void iClickOnTrackPointsSection() throws InterruptedException {
        ValueCenter.TrackPoints();
    }

    @And("Verify loggedIn user track {string}")
    public void verifyLoggedInUserTrack(String page) throws InterruptedException {
        switch (page) {
            case "Points":
                Assert.assertTrue("Loyalty Points is not Matched", ValueCenter.LoyaltyPoints());
                   break;
            case "Rewards":
                Assert.assertTrue("Loyalty Rewards is not Matched", ValueCenter.LoyaltyRewards());
                break;
            default:
                throw new RuntimeException("Unknown page or no handle for page: " + page );
        }
    }

    @Then("^I click on Earn and Redeem section$")
    public void iClickOnEarnAndRedeemSection() throws InterruptedException {
        ValueCenter.EarnRedeem();

    }

    @And("Verify loggedIn user EarnRedeem {string}")
    public void verifyLoggedInUserEarnRedeem(String page) throws InterruptedException {
        switch (page) {
            case "section":
                Assert.assertTrue("Earn & Redeem section is not Matched", ValueCenter.EarnRedeemSection());
                break;
            case "Rewards":
                Assert.assertTrue("Earn & Redeem Rewards is not Matched", ValueCenter.EarnRedeemRewards());
                break;
            case "PointsBalance":
                Assert.assertTrue("Earn & Redeem PointsBalance is not Matched", ValueCenter.EarnRedeemPointsBalance());
                break;
            default:
                throw new RuntimeException("Unknown page or no handle for page: " + page );
        }
    }

    Long loyaltyPoints = 0L;
    Long earnedOrBurnedPts = 0L;

    @Then("I check available loyalty points")
    public void iCheckAvailableLoyaltyPoints() {
        ValueCenter.TrackPoints();
        loyaltyPoints = ValueCenter.getLoyaltyPoints();
        System.out.println("Existing points: " + loyaltyPoints);
    }

    @And("^I validate (earned|burned) points in Track Points section$")
    public void iValidatePointsInTrackPointsSection(String usage) {
        ValueCenter.TrackPoints();
        Long actualPoints = ValueCenter.getLoyaltyPoints();
        System.out.println("Actual points: " + actualPoints);
        System.out.println(usage + " points: " + earnedOrBurnedPts);
        long expectedPoints = usage.equals("burned") ? loyaltyPoints - earnedOrBurnedPts : loyaltyPoints + earnedOrBurnedPts;
        System.out.println("Expected points: " + expectedPoints);
        Assert.assertEquals(expectedPoints, (long) actualPoints);
        String latestPointsInActivity = ValueCenter.getLatestPointsInActivity();
        Assert.assertEquals((usage.equals("burned")  ? "-"  : "+") + earnedOrBurnedPts, StringUtils.trimToEmpty(latestPointsInActivity));
    }

    @And("I donate amount to a random organization")
    public void iDonateAmountToARandomOrganization() {
        ValueCenter.EarnRedeem();
        ValueCenter.POPUP();
        ValueCenter.selectOrganization();
        String pointsToBeDeducted = ValueCenter.selectDonationAmount();
        System.out.println("pointsToBeDeducted: " + pointsToBeDeducted);
        earnedOrBurnedPts = Optional.ofNullable(pointsToBeDeducted)
                .map(str -> str.replaceAll("\\D", ""))
                .filter(StringUtils::isNumeric).map(Long::parseLong).orElse(0L);
        ValueCenter.clickDonate();
        Assert.assertTrue(ValueCenter.isDonationSuccess());
    }

    @And("I apply bonus points promo code")
    public void iApplyBonusPointsPromoCode() throws Exception {
        ValueCenter.EarnRedeem();
        List<String> promoCodes = TestData.getPromoCodes();
        for (String code : promoCodes) {
            String message = ValueCenter.applyPromoCode(code);
            if (StringUtils.isEmpty(message) || message.toLowerCase().contains("unable to convert this bonus cash")) {
                System.out.println("Applied promo code is Invalid or expired. PromoCode: " + code);
            } else {
                System.out.println("Promo code (" + code + ") applied successfully. Message: " + message);
                String pointsEarned = message.replaceAll("\\D", "");
                earnedOrBurnedPts = Optional.of(pointsEarned).filter(StringUtils::isNumeric)
                        .map(Long::parseLong).orElse(0L);
                break;
            }
        }

    }
}