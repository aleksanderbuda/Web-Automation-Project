Feature: Find Transactions

  Scenario: User can find a transaction by ID
    Given the user is on the Landing Page
    When the user registers a new account
    And the user opens a new bank account
    And the user transfers funds
    And the user navigates to Account Activity page
    And the user selects a transaction
    And the user opens the Find Transactions page
    And the user searches for the transaction by ID
    Then the transaction should be found by ID

  Scenario: User can find a transaction by date
    Given the user is on the Landing Page
    When the user registers a new account
    And the user opens a new bank account
    And the user transfers funds
    And the user navigates to Account Activity page
    And the user selects a transaction
    And the user opens the Find Transactions page
    And the user searches for the transaction by date
    Then the transaction should be found by date

  Scenario: User can find a transaction by amount
    Given the user is on the Landing Page
    When the user registers a new account
    And the user opens a new bank account
    And the user transfers funds
    And the user navigates to Account Activity page
    And the user selects a transaction
    And the user opens the Find Transactions page
    And the user searches for the transaction by amount
    Then the transaction should be found by amount
