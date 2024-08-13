Feature: Create Account Page

  Scenario: User is redirected to Create Account Page from Landing Page
    Given I am on the landing page
    And I click the Register button
    Then I am redirected to the Create Account Page

  Scenario: User successfully registers an account
    Given I am on the landing page
    And I navigate to Registration page
    And I register new account
    When I fill in the registration form with valid details
    And I click Create Account button
    Then I see a welcome message after registration
    When I click Accounts Overview button
    Then I see Accounts Overview page

  Scenario: User receives error messages for required registration fields
    Given I am on the landing page
    And I navigate to Registration page
    When I click "Create Account" button with empty form
    Then I see error messages for all required fields

  Scenario: First name is saved with leading and trailing spaces during registration
    Given I am on the landing page
    And I click the Register button
    Then I am redirected to the Create Account Page
    When I fill in the correct form with first name having leading and trailing spaces
    And I click Create Account button
    Then I see confirmation that first name is saved with leading and trailing spaces
