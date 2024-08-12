Feature: Request Loan Page

  Scenario: Verify user pays less than 10% for down payment
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user opens the Request Loan page
    And the user requests a loan with a down payment less than 10% of the loan amount
    Then the user should see an error message and the loan status should be Denied

  Scenario: Verify user pays more down payment than balance allows
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user opens the Request Loan page
    And the user requests a loan with a down payment more than their balance allows
    Then the user should see an error message and the loan status should be Denied

  Scenario: Verify user can take a loan
    Given the user is on the Landing Page
    When the user registers a new account with valid details
    And the user opens the Request Loan page
    And the user requests a loan with a valid down payment
    Then the user should see a success message and the loan status should be Approved
    And the user should be able to view the new loan account in Account Activity
