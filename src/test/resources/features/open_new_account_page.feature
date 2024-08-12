Feature: Open New Account Page

  Scenario: Verify user can open 'Checking' type account
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user opens a new checking account
    Then the user should see a new checking account with the correct account number
    And the account balance should be updated correctly

  Scenario: Verify user can open 'Savings' type account
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user opens a new savings account
    Then the user should see a new savings account with the correct account number
    And the account balance should be updated correctly
