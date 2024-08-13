Feature: Bill Payment

  @bill
  Background:
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account

  Scenario: User successfully pays a bill from a single account
    Given I navigate to the Accounts Overview page
    When I navigate to Bill Pay Page
    And I fill random Bill details
    And I submit the Bill Payment
    And I should see a bill payment confirmation message
    And I navigate back to the Accounts Overview page
    Then I verify the transaction details