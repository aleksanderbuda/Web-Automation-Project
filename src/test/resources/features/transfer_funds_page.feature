Feature: Transfer Funds

  Scenario: Transfer funds between accounts
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I open new bank account
    When I transfer funds from the first account to the second account
    And I see the transfer completed page
    Then I see account balances are updated correctly

  Scenario: Check if the transfer can be found in the Account Activity in the matching month
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I open new bank account
    When I transfer funds from the first account to the second account
    Then I verify the transaction presence or absence as per the month
