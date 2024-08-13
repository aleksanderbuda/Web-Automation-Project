Feature: Admin Page Setup

  @admin
  Scenario: User sets up Admin Page
    Given I am on the landing page
    When I navigate to the admin page
    And I clean the database
    And I set the running status
    And I set the balance
    And I set the threshold
    And I submit the changes
    Then I see the success message "Settings saved successfully." should be displayed