Feature: Landing Page Actions

  Scenario: Verify opening the About Page from Landing Page
    Given the user is on the Landing Page
    When the user opens the About Page
    Then the 'About' link should be positioned at the bottom of the page
    And the user should be redirected to the About Page
    And the About Page title should be correct

  Scenario: Verify user login with valid account credentials
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user logs out
    And the user logs in with valid credentials
    Then the user should be redirected to the Accounts Overview Page
