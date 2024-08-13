Feature: Open New Account Page

  Scenario: Verify user can open 'Checking' type account
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I open new bank account
    When I open a checking account
    Then I verify checking account is opened

  Scenario: Verify user can open 'Savings' type account
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    And I open new bank account
    When I open savings account
    Then I verify savings account is opened

