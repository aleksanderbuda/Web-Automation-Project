Feature: Landing Page Actions

  Scenario: Verify navigation to the About Page from the Landing Page
    Given I am on the landing page
    When I check if the "About" link should is positioned at the bottom of the page
    And I click on the "About" link
    And I am redirected to the About Page if the link is in correct place
    Then I verify that About Page title is correct

  Scenario: Verify user can login with valid account credentials
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    When I log out
    And I log in  with valid credentials
    Then I am redirected to the Accounts Overview Page after logging in
