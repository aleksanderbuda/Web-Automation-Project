Feature: Create Account Page

  Scenario: User is redirected to Create Account Page from Landing Page
    Given the user is on the landing page
    When the user clicks the Register button
    Then the user should be redirected to the Create Account Page

  Scenario: User successfully registers an account
    Given the user is on the Create Account Page
    When the user fills in the registration form with valid details
    And the user clicks the Create Account button
    Then the user should see a welcome message
    And the user should be redirected to the Accounts Overview Page

  Scenario: User receives error messages for required registration fields
    Given the user is on the Create Account Page
    When the user clicks the Create Account button without filling in the form
    Then the user should see error messages for all required fields

  Scenario: User's first name is saved with leading and trailing spaces
    Given the user is on the Create Account Page
    When the user fills in the first name with leading and trailing spaces
    And the user fills in the registration form with valid details
    And the user clicks the Create Account button
    Then the first name should be saved with leading and trailing spaces
