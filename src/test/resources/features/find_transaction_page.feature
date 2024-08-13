Feature: Find Transactions

  Scenario: User can find a transaction by ID
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to New Bank account page
    And I open new bank account
    And I transfer funds
    And I navigate to Account Activity page
    And I select a transaction
    When I navigate to Find Transactions page
    And I search for the transaction by ID
    Then I see confirmation for transaction found by ID

  Scenario: User can find a transaction by date
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to New Bank account page
    And I open new bank account
    And I transfer funds
    And I navigate to Account Activity page
    And I select a transaction
    When I navigate to Find Transactions page
    And I search for transaction by date
    Then I see confirmation for transaction found by date

  Scenario: User can find a transaction by amount
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I navigate to New Bank account page
    And I open new bank account
    And I transfer funds
    And I navigate to Account Activity page
    And I select a transaction
    When I navigate to Find Transactions page
    And I search for transaction by amount
    Then I see confirmation for transaction found by amount
