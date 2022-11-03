Feature: Verify different ValueCenter flow

  #VC Page Navigation
  Scenario Outline: Check user is on ValueCenter Page
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    And verify loggedIn user "Value center page"
    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |

# VC Banner page validation
  Scenario Outline: Validating value center Page Banner
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    And verify loggedIn user "First and Last Names"
    And verify loggedIn user "Tier Name"
    And verify loggedIn user "Rewards amount"
    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |
#
#VC Tier progress validation
  Scenario Outline: Validating value center tier progress section
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    And Verify tier progress "section"
    And Verify tier progress "Name"
    And Verify tier progress "tierPoints"
    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |

#  #VC Track Points validation
  Scenario Outline: Validating value center track points section
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    Then I click on Track Points section
    And Verify loggedIn user track "Points"
    And Verify loggedIn user track "Rewards"

    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |

  #VC Earn & Redeem validation
  Scenario Outline: Validating value center Earn & Redeem section
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    Then I click on Earn and Redeem section
    And Verify loggedIn user EarnRedeem "section"
    And Verify loggedIn user EarnRedeem "Rewards"
    And Verify loggedIn user EarnRedeem "PointsBalance"

    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |

  Scenario Outline: Validating value center Donation functionality
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    And I click on My Points And Rewards
    When I check available loyalty points
    And I donate amount to a random organization
    Then I validate burned points in Track Points section
    Examples:
      | Tier | Email id               | password  |
      | Core | AutomationQA1@gmail.com | P@ssword1 |
#  100 points (1$) donated.

##  Scenario Outline: Validating value center BB promo codes functionality (In progress)
#    And I login with "<Tier>" user "<Email id>" and "<password>"
#    And I click on My Points And Rewards
#    When I check available loyalty points
#    And I apply bonus points promo code
#    Then I validate earned points in Track Points section
#    Examples:
#      | Tier | Email id               | password  |
#      | Core | AutomationQA1@gmail.com | P@ssword1 |

#  2 points converted from Bonus Cash.