Feature: Transfer Funds

  Scenario: Transfer funds between accounts
    Given the user is on the Landing Page
    When the user registers a new account
    And the user opens a new bank account
    And the user transfers funds between accounts
    Then the user should see the transfer completed message
    And the user should see updated balances

  Scenario: Check if the transfer can be found in the Account Activity in the matching month
    Given the user is on the Landing Page
    When the user registers a new account
    And the user opens a new bank account
    And the user transfers funds
    Then the user should find the transfer in the Account Activity for the correct month
