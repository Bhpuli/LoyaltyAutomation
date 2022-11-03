@TestNG
@severity=blocker
Feature: Verify different login flow

#  Scenario: Check user is able to login Gap Site
#    Given user launched Gap site
#    And close POPUP windows
#    When I select SignIn dropdown
#    And I click on SignIn button
#    Then I should be on the "login" page
#    And I should enter "Core" user valid email and click on continue
#    Then I should be on the "password" page
#    And I should enter "Core" user valid password and click on signin
#    Then I should be on the "my account" page


  Scenario Outline:  Check user is able to login Gap Site
    Given user launched Gap site
    And I login with "<Tier>" user "<Email id>" and "<password>"
    Then I should be on the "my account" page
    Examples:

      | Tier | Email id               | password  |
      | Core | AutomationQA@gmail.com | P@ssword1 |
      | Core | AutomationQA@gmail.com | P@ssword1 |


