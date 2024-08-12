Feature: Admin Page Setup

  @admin
  Scenario: User sets up Admin Page
    Given the user is on the landing page
    When the user navigates to the admin page
    And cleans the database
    And sets the running status
    And sets the balance
    And sets the threshold
    And submits the changes
    Then the success message "Settings saved successfully." should be displayed