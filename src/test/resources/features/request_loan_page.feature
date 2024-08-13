Feature: Request Loan Page

  Scenario: Verify user pays less than 10% for down payment
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to Request Loan page
    When I request a loan with a down payment less than 10% of the loan amount
    Then I see an error message and the loan status is Denied

  Scenario: Verify user pays more down payment than balance allows
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to Request Loan page
    When I request a loan with a down payment more than balance allows
    Then I see an error message and the loan status is Denied

  Scenario: Verify user can take a loan
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to Request Loan page
    When I request a loan with a valid down payment
    And I see a success message and the loan is Approved
    Then I see the new loan account in Account Activity
