Feature: Bill Payment

  @bill
  Scenario: User is able to pay bills from a single account
    Given the user is on the landing page and registers a new account
    And the user navigates to the Accounts Overview page
    When the user navigates to the Bill Pay page
    And the user fills in the Bill Pay details
    And the user submits the Bill Payment
    Then the user verifies the transaction details
